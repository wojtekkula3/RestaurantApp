package com.wojciechkula.goodtime.initializer

import android.app.Application
import com.wojciechkula.goodtime.config.AppBuildConfig
import timber.log.Timber
import javax.inject.Inject

class TimberInitializer @Inject constructor() : AppInitializer{

    override fun init(application: Application) {
        if (AppBuildConfig.isDebug)
            Timber.plant(Timber.DebugTree())
    }
}