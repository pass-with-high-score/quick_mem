package com.pwhs.quickmem.presentation.ads

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.pwhs.quickmem.core.utils.AppConstant.BANNER_ADS_ID
import com.revenuecat.purchases.CustomerInfo
import com.revenuecat.purchases.Purchases
import com.revenuecat.purchases.PurchasesError
import com.revenuecat.purchases.interfaces.ReceiveCustomerInfoCallback
import timber.log.Timber

@Composable
fun BannerAds(modifier: Modifier = Modifier) {
    var customer: CustomerInfo? by remember { mutableStateOf(null) }
    LaunchedEffect(key1 = true) {
        Purchases.sharedInstance.getCustomerInfo(object : ReceiveCustomerInfoCallback {
            override fun onError(error: PurchasesError) {
                Timber.e("Error getting customer info: $error")
            }

            override fun onReceived(customerInfo: CustomerInfo) {
                Timber.d("Customer info: $customerInfo")
                customer = customerInfo
            }

        })
    }
    if (customer?.activeSubscriptions?.isEmpty() == true) {
        AndroidView(
            modifier = modifier
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
}