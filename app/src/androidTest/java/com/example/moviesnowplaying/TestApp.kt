package com.example.moviesnowplaying

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TestApp)
            modules(emptyList())  // start without any modules
        }
    }

    /**
     *  call before the activity is launched
     */
    internal fun inject(
        modules: List<Module>
    ) {
        loadKoinModules(modules)
    }
}