package com.example.alexandr.mytranslatormvvmkotlin.frameworks.application

import android.app.Application
import com.example.alexandr.mytranslatormvvmkotlin.frameworks.di.application
import com.example.alexandr.mytranslatormvvmkotlin.frameworks.di.historyScreen
import com.example.alexandr.mytranslatormvvmkotlin.frameworks.di.mainScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TranslatorApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(listOf(application, mainScreen, historyScreen))
        }
    }
}
