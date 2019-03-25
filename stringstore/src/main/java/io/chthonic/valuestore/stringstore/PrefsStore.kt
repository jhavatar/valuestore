package io.chthonic.valuestore.stringstore

/**
 * Created by jhavatar on 2/17/2019.
 */
import android.content.Context
import android.content.SharedPreferences

class PrefsStore(storeKey: String, appContext: Context) : StringStore {

    private val prefs: SharedPreferences by lazy {
        appContext.getSharedPreferences(storeKey, android.content.Context.MODE_PRIVATE)
    }

    override fun set(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    override fun get(key: String, fallback: String?): String? {
        return prefs.getString(key, fallback)
    }

    override fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }
}