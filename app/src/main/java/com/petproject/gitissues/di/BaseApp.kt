package com.petproject.gitissues.di

import android.app.Application
import com.petproject.gitissues.di.components.AppComponent
import com.petproject.gitissues.di.components.DaggerAppComponent
import com.petproject.gitissues.di.modules.AppModule

public class BaseApp : Application() {
    val component: AppComponent = DaggerAppComponent.builder().appModule(AppModule((this))).build()
}