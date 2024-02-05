package kmp.cartwave.store

import android.app.Application
import di.initKoin

class CartWaveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        initKoin()
    }

    companion object {
        lateinit var instance: CartWaveApp
            private set
    }
}