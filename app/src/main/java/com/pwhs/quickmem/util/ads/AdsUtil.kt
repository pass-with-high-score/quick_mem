package com.pwhs.quickmem.util.ads

import android.content.Context
import android.widget.Toast
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnPaidEventListener
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback
import com.pwhs.quickmem.MainActivity
import com.pwhs.quickmem.core.utils.AppConstant.INTERSTITIAL_ADS_ID
import com.pwhs.quickmem.core.utils.AppConstant.REWARDED_INTERSTITIAL_ADS_ID
import com.pwhs.quickmem.core.utils.AppConstant.REWARD_ADS_ID

object AdsUtil {
    fun interstitialTestAd(context: Context) {
        // Load an ad
        InterstitialAd.load(
            context,
            INTERSTITIAL_ADS_ID,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    // The interstitial ad is loaded
                    Toast.makeText(context, "Ad Loaded", Toast.LENGTH_SHORT).show()
                    if (context is MainActivity) {
                        interstitialAd.show(context)
                    }

                    interstitialAd.onPaidEventListener =
                        OnPaidEventListener { p0 ->
                            Toast.makeText(
                                context,
                                p0.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // The interstitial ad failed to load
                    Toast.makeText(context, adError.message, Toast.LENGTH_SHORT).show()

                    adError.cause?.let {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }

                    adError.responseInfo?.let {
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                    }

                    adError.domain.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }

                    adError.code.let {
                        Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
                    }

                    adError.message.let {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        )
    }

    fun rewardedTestAd(context: Context, onAdWatched: () -> Unit) {
        // Load an ad
        RewardedAd.load(
            context,
            REWARD_ADS_ID,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(rewardedAd: RewardedAd) {
                    // The interstitial ad is loaded
                    Toast.makeText(context, "Ad Loaded", Toast.LENGTH_SHORT).show()
                    if (context is MainActivity) {
                        rewardedAd.show(context) {
                            // The user earned the reward
                            onAdWatched()
                        }
                    }
                    Toast.makeText(context, rewardedAd.rewardItem.type, Toast.LENGTH_SHORT).show()
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    // The interstitial ad failed to load
                    Toast.makeText(context, adError.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    fun rewardedInterstitialTestAd(context: Context, onAdWatched: () -> Unit) {
        // Load an ad
        RewardedInterstitialAd.load(
            context,
            REWARDED_INTERSTITIAL_ADS_ID,
            AdRequest.Builder().build(),
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(error: LoadAdError) {
                    super.onAdFailedToLoad(error)
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()


                }

                override fun onAdLoaded(adLoaded: RewardedInterstitialAd) {
                    super.onAdLoaded(adLoaded)
                    Toast.makeText(context, "Ad Loaded", Toast.LENGTH_SHORT).show()

                    if (context is MainActivity) {
                        adLoaded.show(context) {
                            // The user earned the reward
                            onAdWatched()
                        }
                    }

                    adLoaded.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent()
                            Toast.makeText(context, "Ad Dismissed", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAdShowedFullScreenContent() {
                            super.onAdShowedFullScreenContent()
                            Toast.makeText(context, "Ad Showed", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAdClicked() {
                            super.onAdClicked()
                            Toast.makeText(context, "Ad Clicked", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAdImpression() {
                            super.onAdImpression()
                            Toast.makeText(context, "Ad Impression", Toast.LENGTH_SHORT).show()
                        }

                        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                            super.onAdFailedToShowFullScreenContent(p0)
                            Toast.makeText(context, p0.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
        )
    }
}