package com.moondroid.wordcomplete.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.moondroid.wordcomplete.R
import com.moondroid.wordcomplete.data.model.Item
import com.moondroid.wordcomplete.utils.Extension.debug
import com.moondroid.wordcomplete.utils.ItemHelper

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ItemHelper.init(this)
    }

    /*fun showAd(onFinished: () -> Unit) {
        try {
            InterstitialAd.load(
                this,
                getString(R.string.foreground_id),
                AdRequest.Builder().build(),
                object : InterstitialAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        debug("AdError: ${adError.message}")
                    }

                    override fun onAdLoaded(interstitialAd: InterstitialAd) {
                        interstitialAd.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                onFinished()
                            }
                        }

                        interstitialAd.show(this@MainActivity)
                    }
                })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
}