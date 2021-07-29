package com.petproject.gitissues

import android.app.Application
import com.petproject.gitissues.di.components.AppComponent
import com.petproject.gitissues.di.components.DaggerAppComponent
import com.petproject.gitissues.di.modules.AppModule
import com.petproject.gitissues.di.modules.DaoModule
import com.petproject.gitissues.di.modules.NetworkModule

class BaseApp : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component =
            DaggerAppComponent.builder().appModule(AppModule((this))).daoModule(DaoModule(this))
                .networkModule(
                    NetworkModule()
                )
                .build()
    }
}