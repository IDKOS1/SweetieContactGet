package com.example.sweetcontactget.data

import android.app.Application
import android.content.Context

class ContactApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: ContactApplication

        fun applicationContext(): Context {
            return instance.applicationContext
        }
    }
}