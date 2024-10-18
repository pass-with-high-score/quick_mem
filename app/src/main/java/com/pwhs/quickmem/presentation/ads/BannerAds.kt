package com.pwhs.quickmem.presentation.ads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.pwhs.quickmem.core.utils.AppConstant.BANNER_ADS_ID

@Composable
fun BannerAds(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(
                    AdSize.BANNER
                )
                adUnitId = BANNER_ADS_ID
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}