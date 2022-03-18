package com.wojciechkula.goodtime

import android.app.Application
import com.wojciechkula.goodtime.initializer.AppInitializersContainer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class GoodTimeApp : Application() {

    @Inject
    lateinit var initializersContainer: AppInitializersContainer

    override fun onCreate() {
        super.onCreate()
        initializersContainer.init(this)
    }
}