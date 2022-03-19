package com.wojciechkula.goodtime.di

import com.wojciechkula.goodtime.initializer.AppInitializersContainer
import com.wojciechkula.goodtime.initializer.TimberInitializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppInitializerModule {

    @Provides
    fun provideAppInitializersContainer(timberInitializer: TimberInitializer) =
        AppInitializersContainer(timberInitializer)
}