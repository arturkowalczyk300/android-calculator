package com.arturkowalczyk300.calculator.other.preferences

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(val context: Context) {
    private val PREFS_FILE_NAME = "com.arturkowalczyk300.calculator.PREFS_FILE"
    private val PREF_ADVANCED_OPERATIONS_MODE_ENABLED = "com.arturkowalczyk300.calculator.PREFS_FILE_ADVANCED_OPERATIONS_ENABLED"

    var sharedPreferencesInstance: SharedPreferences

    init{
        sharedPreferencesInstance = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun isAdvancedOperationsModeEnabled(): Boolean{
        return sharedPreferencesInstance.getBoolean(PREF_ADVANCED_OPERATIONS_MODE_ENABLED, false)
    }

    fun setAdvancedOperationsModeEnabled(value: Boolean){
        sharedPreferencesInstance
            .edit()
            .putBoolean(PREF_ADVANCED_OPERATIONS_MODE_ENABLED, value)
            .apply()
    }
}