package kmp.cartwave.store

import android.app.Application

class CartWaveApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: CartWaveApp
            private set
    }
}