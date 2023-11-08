package com.moondroid.wordcomplete.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.moondroid.wordcomplete.BuildConfig
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.data.model.BaseResponse
import com.moondroid.wordcomplete.data.model.Item
import com.moondroid.wordcomplete.data.model.ItemResponse
import com.moondroid.wordcomplete.databinding.FragmentSplashBinding
import com.moondroid.wordcomplete.delegate.viewBinding
import com.moondroid.wordcomplete.network.MyRetrofit
import com.moondroid.wordcomplete.network.RetrofitExService
import com.moondroid.wordcomplete.utils.Constants
import com.moondroid.wordcomplete.utils.Extension.debug
import com.moondroid.wordcomplete.utils.Extension.visible
import com.moondroid.wordcomplete.utils.ItemHelper
import com.moondroid.wordcomplete.utils.firebase.FBCrash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val binding by viewBinding(FragmentSplashBinding::bind)
    private lateinit var mContext: MainActivity

    private var oneButtonDialog: OneButtonDialog? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkAppVersion()
        initView()
    }

    private fun checkAppVersion() {
        val service = MyRetrofit.retrofit.create(RetrofitExService::class.java)
        service.checkAppVersion(
            BuildConfig.VERSION_CODE,
            BuildConfig.VERSION_NAME,
            mContext.packageName
        ).enqueue(object : Callback<BaseResponse> {
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        when (it.code) {
                            Constants.ResponseCode.INACTIVE -> {
                                update()
                            }

                            Constants.ResponseCode.NOT_EXIST -> {
                                Toast.makeText(mContext, "존재하지 않는 버전입니다.", Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                setItems()

                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                FBCrash.logException(t)
                setItems()
            }
        })
    }

    private fun setItems() {
        val service = MyRetrofit.retrofit.create(RetrofitExService::class.java)
        service.getItems().enqueue(object : Callback<ItemResponse> {
            override fun onResponse(call: Call<ItemResponse>, response: Response<ItemResponse>) {
                response.body()?.let {
                    ItemHelper.items = ArrayList(it.body)
                    ItemHelper.items.shuffle()
                    debug("ITEMS : ${ItemHelper.items}")
                    binding.btnStart.isEnabled = true
                }
            }

            override fun onFailure(call: Call<ItemResponse>, t: Throwable) {
                val message = "게임을 진행할 수 없습니다."
                val onClick: () -> Unit = {
                    activity?.finish()
                }
                FBCrash.logException(t)
                oneButtonDialog?.let {
                    it.msg = message
                    it.onClick = onClick
                } ?: run {
                    oneButtonDialog = OneButtonDialog(mContext, message, onClick)
                }
                oneButtonDialog?.show()
            }
        })
    }

    private fun update() {
        val message = "새로운 버전이 나왔습니다.\n플레이 스토어에서 업데이트 해주세요."
        val onClick = {
            val updateIntent = Intent(Intent.ACTION_VIEW)
            updateIntent.data = Uri.parse("market://details?id=${mContext.packageName}")
            startActivity(updateIntent)
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

        animation.setAnimationListener(object : AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.btnStart.visible()
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })

        binding.btnStart.setOnClickListener {
            findNavController().navigate(SplashFragmentDirections.splashToQuiz())
        }
    }
}