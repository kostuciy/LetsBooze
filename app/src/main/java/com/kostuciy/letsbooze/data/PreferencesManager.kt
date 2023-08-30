package com.kostuciy.letsbooze.data

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kostuciy.letsbooze.companies.CompanyMember

private const val COMPANY_PREFERENCES = "company_preferences_file"
//private const val GAME_STATE_PREFERENCES = "Game state"

class PreferencesManager private constructor(
    private val applicationContext: Application
) {
    private lateinit var _preferences: SharedPreferences

    val preferences
        get() = _preferences //TODO: check if works

    fun setPreferences(/*preferencesFileName: String*/) { //TODO: add option to use different preferences (if needed)
        _preferences = applicationContext.getSharedPreferences(
            /*preferencesFileName*/COMPANY_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun <T> put(classObject: T, key: String) {
        //Convert object to JSON String.
        val jsonString = GsonBuilder().create().toJson(classObject)

        //Save that String in SharedPreferences
        preferences
            .edit()
            .putString(key, jsonString)
            .apply()
    }

    inline fun <reified T> getValue(key: String): T? { //TODO: find out if "operator" needed
        //We read JSON String which was saved.
        val retrievedValue = preferences.getString(key, "")
        //JSON String was found which means object can be read.
        //We convert this JSON String to model object. Parameter "c" (of
        //type Class < T >" is used to cast.
//        return GsonBuilder().create().fromJson(retrievedValue, T::class.java)
        return GsonBuilder().create().fromJson(retrievedValue, T::class.java)
    }


    companion object {
        private var instance: PreferencesManager? = null

        fun initialize(application: Application) {
            if (instance == null)
                instance = PreferencesManager(application)
        }

        fun get(): PreferencesManager =
            instance ?: throw IllegalStateException(
                "ModelPreferencesManager must be initialized."
            )
    }
}