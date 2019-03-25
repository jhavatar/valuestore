package io.chthonic.valuestore.example

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.chthonic.valuestore.stringstore.CacheStorageStore
import io.chthonic.valuestore.stringstore.InternalStorageStore
import io.chthonic.valuestore.stringstore.PrefsStore
import io.chthonic.valuestore.stringstore.StringStore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        const val STORE_KEY = "store_key"
        const val FALLBACK_VAL = ""
    }

    private val prefsStore: StringStore by lazy {
        PrefsStore("example", applicationContext)
    }

    private val internalStore: StringStore by lazy {
        InternalStorageStore("example", applicationContext)
    }

    private val cacheStore: StringStore by lazy {
        CacheStorageStore(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_load.setOnClickListener {
            loadStringsFromStore()
        }

        btn_save.setOnClickListener {
            saveStringsToStore()
        }

        loadStringsFromStore()
    }


    fun saveStringsToStore() {
        val data = StringData(txt_prefs.text.toString(),
            txt_file.text.toString(),
            txt_cache.text.toString())

        GlobalScope.launch(Dispatchers.Main) {
            GlobalScope.async(Dispatchers.IO) {
                prefsStore.set(STORE_KEY, data.prefs)
                internalStore.set(STORE_KEY, data.file)
                cacheStore.set(STORE_KEY, data.cache)
            }.await()
            Snackbar.make(root_view, "Save success", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun loadStringsFromStore() {
        GlobalScope.launch(Dispatchers.Main) {
            val data = GlobalScope.async(Dispatchers.IO) {
                StringData(
                    prefsStore.get(STORE_KEY, FALLBACK_VAL) ?: FALLBACK_VAL,
                    internalStore.get(STORE_KEY, FALLBACK_VAL) ?: FALLBACK_VAL,
                    cacheStore.get(STORE_KEY, FALLBACK_VAL) ?: FALLBACK_VAL
                )
            }.await()

            txt_prefs.setText(data.prefs)
            txt_file.setText(data.file)
            txt_cache.setText(data.cache)
            Snackbar.make(root_view, "Load success", Snackbar.LENGTH_SHORT).show()
        }
    }


    data class StringData(val prefs: String, val file: String, val cache: String)
}
