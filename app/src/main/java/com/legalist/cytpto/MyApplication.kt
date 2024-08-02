package com.legalist.cytpto

import android.app.Application

import com.legalist.cytpto.di.appModule
import com.legalist.cytpto.di.module
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {

      androidContext(this@MyApplication)
          modules(appModule, module)

        }
    }
}