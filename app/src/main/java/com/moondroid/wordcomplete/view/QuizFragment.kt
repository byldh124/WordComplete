package com.moondroid.wordcomplete.view

import android.annotation.SuppressLint
import android.app.ActionBar.LayoutParams
import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.tts.TextToSpeech
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.databinding.FragmentQuizBinding
import com.moondroid.wordcomplete.delegate.viewBinding
import com.moondroid.wordcomplete.utils.Extension.afterTextChanged
import com.moondroid.wordcomplete.utils.Extension.dpToPixel
import com.moondroid.wordcomplete.utils.Extension.getDrawableId
import com.moondroid.wordcomplete.utils.Extension.shuffle
import com.moondroid.wordcomplete.utils.Extension.visible
import com.moondroid.wordcomplete.utils.ItemHelper
import java.util.Locale

class QuizFragment : Fragment(R.layout.fragment_quiz) {
    private val binding by viewBinding(FragmentQuizBinding::bind)
    private lateinit var mContext: MainActivity

    private var clickSound: MediaPlayer? = null
    private var correctSound: MediaPlayer? = null
    private var incorrectSound: MediaPlayer? = null

    private val textPadding by lazy {
        dpToPixel(mContext, 8)
    }

    var name = ""
    var stage = 0

    private val shakeAnim by lazy {
        AnimationUtils.loadAnimation(mContext, R.anim.shakeanimation)
    }

    private val list = ArrayList<View>()

    private lateinit var tts: TextToSpeech

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setItem()
    }

    private fun init() {
        // 스피치 츠기화
        tts = TextToSpeech(mContext) {
            if (it != TextToSpeech.ERROR) {
                tts.language = Locale.US
            }
        }.apply {
            setSpeechRate(0.8f)
        }

        // 효과음 초기화
        clickSound = MediaPlayer.create(mContext, R.raw.tab)
        correctSound = MediaPlayer.create(mContext, R.raw.correct)
        incorrectSound = MediaPlayer.create(mContext, R.raw.incorrect)


        // 배너 광고 초기화
        MobileAds.initialize(mContext)
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        binding.tvAnswer.afterTextChanged {
            if (name.isNotEmpty()) {
                checkField(it)
            }
        }

        // 유틸 버튼 Region Start
        binding.icSound.setOnClickListener {
            tts.speak(name, TextToSpeech.QUEUE_FLUSH, null, "uid")
        }

        binding.icReset.setOnClickListener {
            ++stage
            setItem()
        }

        binding.icBack.setOnClickListener {
            if (list.isNotEmpty()) {
                val origin = binding.tvAnswer.text
                binding.tvAnswer.text = origin.substring(0, origin.lastIndex)
                val lastView = list[list.lastIndex]
                lastView.visible()
                list.remove(lastView)
            }
        } // Region End
    }

    /**
     * 아이템 세팅
     **/
    private fun setItem() {
        if (stage > ItemHelper.items.lastIndex) {
            stage = 0
            ItemHelper.items.shuffle()
        }

        // View 초기화
        list.clear()
        binding.tvAnswer.text = ""
        binding.btnWrapper1.removeAllViews()
        binding.btnWrapper2.removeAllViews()
        binding.ivCorrect.visible(false)

        // 아이템 세팅
        val item = ItemHelper.items[stage]

        // 이미지 로드
        //val resName = String.format("image%03d", item.index)
        //val drawable = ContextCompat.getDrawable(mContext, mContext.getDrawableId(resName))
        Glide.with(mContext).load(item.getImage()).into(binding.ivQuiz)

        // 알파벳 버튼
        binding.ko = item.kor
        binding.utilsEnable = true
        name = item.eng
        val name = name.shuffle()

        if (name.length <= 5) {
            name.forEach {
                binding.btnWrapper1.addView(getTextView(it), layoutParams)
            }
        } else {
            name.forEachIndexed { index, c ->
                if (index < name.length / 2) {
                    binding.btnWrapper1.addView(getTextView(c), layoutParams)
                } else {
                    binding.btnWrapper2.addView(getTextView(c), layoutParams)
                }
            }
        }
    }

    private fun checkField(input: String) {
        if (input.length == name.length) {
            checkCorrect(input == name)
        }
    }

    /**
     * 정답 확인
     **/
    private fun checkCorrect(isCorrect: Boolean) {
        binding.utilsEnable = false
        if (isCorrect) {
            binding.ivCorrect.apply {
                Glide.with(mContext).load(R.drawable.answer_o).into(this)
                visible()
                correctSound?.start()
                handler.postDelayed({
                    tts.speak(name, TextToSpeech.QUEUE_FLUSH, null, "uid")
                }, DELAY_OF_RIGHT_SOUND)

                handler.postDelayed({
                    ++stage
                    setItem()
                }, DELAY_OF_TTS)
                /*if (stage != 0 && stage % FOREGROUND_AD_INTERVAL == 0) {
                    mContext.showAd { setItem(++stage) }
                } else {
                    handler.postDelayed({ setItem(++stage) }, DELAY_OF_TTS)
                }*/
            }
        } else {
            binding.ivCorrect.apply {
                Glide.with(mContext).load(R.drawable.answer_x).into(this)
                visible()
                startAnimation(shakeAnim)
                incorrectSound?.start()
                handler.postDelayed({ setItem() }, DELAY_OF_RIGHT_SOUND)
            }
        }
    }

    /**
     * 알파벳 버튼 생성
     **/
    @SuppressLint("SetTextI18n")
    private fun getTextView(char: Char): AppCompatTextView {
        val textView = AppCompatTextView(mContext)
        textView.apply {
            background = ContextCompat.getDrawable(mContext, R.drawable.button_blue02)
            setTextColor(ContextCompat.getColor(mContext, R.color.white))
            typeface = ResourcesCompat.getFont(mContext, R.font.nanum_square_round_eb)
            text = char.toString()
            gravity = Gravity.CENTER
            setPadding(textPadding, textPadding, textPadding, textPadding)
        }

        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(
            textView,
            20,
            48,
            1,
            TypedValue.COMPLEX_UNIT_DIP
        )

        textView.setOnClickListener {
            clickSound?.start()
            val before = binding.tvAnswer.text.toString()
            binding.tvAnswer.text = before + char
            it.visibility = View.INVISIBLE
            list.add(it)
        }

        return textView
    }

    /**
     * 사운드 초기화
     **/
    override fun onDestroy() {
        super.onDestroy()
        clickSound?.let {
            it.stop()
            it.release()
            clickSound = null
        }
        correctSound?.let {
            it.stop()
            it.release()
            correctSound = null
        }
        incorrectSound?.let {
            it.stop()
            it.release()
            incorrectSound = null
        }
    }


    companion object {
        val layoutParams =
            LinearLayoutCompat.LayoutParams(
                0,
                LayoutParams.MATCH_PARENT,
                1.0f
            ).apply {
                setMargins(8, 8, 8, 8)
            }

        const val FOREGROUND_AD_INTERVAL = 13
        const val DELAY_OF_RIGHT_SOUND = 300L
        const val DELAY_OF_TTS = 1300L
    }
}