package com.moondroid.wordcomplete.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.moondroid.wordcomplete.BuildConfig
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.databinding.ActivitySplashBinding
import com.moondroid.wordcomplete.delegate.viewBinding
import com.moondroid.wordcomplete.domain.model.onError
import com.moondroid.wordcomplete.domain.model.onSuccess
import com.moondroid.wordcomplete.domain.respository.Repository
import com.moondroid.wordcomplete.utils.Extension.debug
import com.moondroid.wordcomplete.utils.Extension.visible
import com.moondroid.wordcomplete.utils.ItemHelper
import com.moondroid.wordcomplete.utils.NetworkConnection
import com.moondroid.wordcomplete.utils.ResponseCode
import com.moondroid.wordcomplete.utils.firebase.FBCrash
import com.moondroid.wordcomplete.view.base.BaseActivity
import com.moondroid.wordcomplete.view.base.isShow
import com.moondroid.wordcomplete.view.dialog.DisconnectNetworkDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    @Inject
    lateinit var repository: Repository

    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private var oneButtonDialog: OneButtonDialog? = null
    private val disconnectNetworkDialog by lazy { DisconnectNetworkDialog(mContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
    }

    override fun onResume() {
        super.onResume()
        debug("onResume()-called()")
        NetworkConnection.observe(this, ::networkObserve)
    }

    private fun networkObserve(networkConnected: Boolean) {
        disconnectNetworkDialog.isShow = !networkConnected
        if (networkConnected) {
            debug("check app version()")
            checkAppVersion()
        }
    }

    override fun onPause() {
        super.onPause()
        debug("onPause called()")
        NetworkConnection.removeObservers(this)
    }

    private fun checkAppVersion() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.checkAppVersion(
                BuildConfig.VERSION_CODE,
                BuildConfig.VERSION_NAME,
                mContext.packageName
            ).collect { result ->
                result.onSuccess {
                    when (it) {
                        ResponseCode.INACTIVE -> {
                            update()
                        }

                        ResponseCode.NOT_EXIST -> {
                            Toast.makeText(mContext, "존재하지 않는 버전입니다.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            setItems()
                        }
                    }
                }
            }
        }
    }

    private fun setItems() {
        CoroutineScope(Dispatchers.Main).launch {
            repository.getItem().collect { result ->
                result
                    .onSuccess {
                        ItemHelper.items = it
                        ItemHelper.shuffle()
                        binding.btnStart.isEnabled = true
                    }.onError { t ->
                        val message = "게임을 진행할 수 없습니다."
                        val onClick: () -> Unit = ::finish
                        FBCrash.logException(t)
                        oneButtonDialog?.let {
                            it.msg = message
                            it.onClick = onClick
                        } ?: run {
                            oneButtonDialog = OneButtonDialog(mContext, message, onClick)
                        }
                        oneButtonDialog?.show()
                    }

            }
        }
    }

    private fun update() {
        val message = "새로운 버전이 나왔습니다.\n플레이 스토어에서 업데이트 해주세요."
        val onClick = {
            val updateIntent = Intent(Intent.ACTION_VIEW)
            try {
                updateIntent.data = Uri.parse("market://details?id=${mContext.packageName}")
                startActivity(updateIntent)
            } catch (e: Exception) {
                try {
                    updateIntent.data = Uri.parse("https://play.google.com/store/apps/details?id=${mContext.packageName}")
                    startActivity(updateIntent)
                } catch (e: Exception) {
                    finish()
                }
            }
        }
        oneButtonDialog?.let {
            it.msg = message
            it.onClick = onClick
        } ?: run {
            oneButtonDialog = OneButtonDialog(mContext, message, onClick)
        }
        oneButtonDialog?.show()
    }

    private fun initView() {
        val animation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in)
        binding.logo.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.btnStart.visible()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        binding.btnStart.setOnClickListener {
            startActivity(Intent(mContext, MainActivity::class.java))
            finish()
        }
    }
}