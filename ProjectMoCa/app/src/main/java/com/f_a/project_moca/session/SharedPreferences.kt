package com.f_a.project_moca.session

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(val context: Context) {

    private val PREFS_ID = ""
    val sharedPref: SharedPreferences = context.getSharedPreferences(PREFS_ID, Context.MODE_PRIVATE)

    fun save(KEY_ID: String, text: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.putString(KEY_ID, text)

        editor!!.commit()
    }

    fun getValueString(KEY_ID: String): String? {

        return sharedPref.getString(KEY_ID, null)


    }

    fun clearSharedPreference() {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        //sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        editor.clear()
        editor.commit()
    }

    fun removeValue(KEY_ID: String) {

        val editor: SharedPreferences.Editor = sharedPref.edit()

        editor.remove(KEY_ID)
        editor.commit()
    }
}
