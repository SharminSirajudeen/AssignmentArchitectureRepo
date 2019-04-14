package com.example.assignmentarchitecture.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.assignmentarchitecture.di.PreferenceInfo
import com.google.gson.Gson

import javax.inject.Inject

class PreferencesImpl @Inject
constructor(
    context: Context, @PreferenceInfo prefFileName: String, private val gson: Gson
) : IPreferences {
    override var isLanguageSelected: Boolean
        get() = mPrefs.getBoolean(IS_LANGUAGE_SELECTED, true)
        set(value) {}

    private val mPrefs: SharedPreferences

    init {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)
    }

    override fun clearAllPref() {
        mPrefs.edit().clear().apply()
    }

    companion object {
        val IS_LANGUAGE_SELECTED = "is_language_selected"
    }
}
