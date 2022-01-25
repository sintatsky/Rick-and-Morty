package com.sintatsky.ramproject.presentation.app

import android.app.Application
import com.sintatsky.ramproject.presentation.di.Injector

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.createAppComponent(this)
    }
}