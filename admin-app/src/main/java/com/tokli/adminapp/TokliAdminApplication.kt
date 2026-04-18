package com.tokli.adminapp

import android.app.Application
import com.google.firebase.FirebaseApp

class TokliAdminApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}
