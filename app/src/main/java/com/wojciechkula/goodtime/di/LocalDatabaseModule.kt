package com.wojciechkula.goodtime.di

import android.content.Context
import com.wojciechkula.goodtime.data.dao.ProductDao
import com.wojciechkula.goodtime.data.database.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class LocalDatabaseModule {

    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return LocalDatabase.getDatabase(appContext)
    }

    @Provides
    fun provideProductDao(database: LocalDatabase) : ProductDao {
        return database.productsDao()
    }
}