package com.yoji0806.to_doapp

import android.app.Application
import io.realm.Realm

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)
    }
}