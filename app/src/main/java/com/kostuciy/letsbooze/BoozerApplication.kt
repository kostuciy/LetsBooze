package com.kostuciy.letsbooze

import android.app.Application
import com.kostuciy.letsbooze.data.InternalStorageManager
import com.kostuciy.letsbooze.data.PreferencesManager

class BoozerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PreferencesManager.initialize(this)
        InternalStorageManager.initialize(this)
    }
}