package com.alexandros.p.gialamas.duetodo

import android.app.Application
import android.content.Context

class MyDueToDoApplication : Application() {
    companion object {
        lateinit var instance : MyDueToDoApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}