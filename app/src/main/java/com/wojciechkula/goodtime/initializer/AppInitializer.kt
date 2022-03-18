package com.wojciechkula.goodtime.initializer

import android.app.Application

interface AppInitializer {
    fun init(application: Application)
}