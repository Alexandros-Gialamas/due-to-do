package com.alexandros.p.gialamas.duetodo.di

import android.app.Application
import android.content.Context
import com.alexandros.p.gialamas.duetodo.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: BaseApplication) : Context = application.applicationContext

}