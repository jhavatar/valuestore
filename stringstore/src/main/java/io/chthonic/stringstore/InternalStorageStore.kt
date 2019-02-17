package io.chthonic.stringstore

/**
 * Created by jhavatar on 2/17/2019.
 */
import android.content.Context
import android.util.Log
import okio.buffer
import okio.sink
import okio.source
import java.io.File


class InternalStorageStore(private val storeFolderName: String, private val appContext: Context): StringStore {
    companion object {
        val LOG_TAG: String by lazy {
            InternalStorageStore::class.java.simpleName
        }
    }

    private fun getDir(): File {
        return appContext.getDir(storeFolderName, Context.MODE_PRIVATE)
    }

    private fun getFile(fileName: String): File {
        return File(getDir(), fileName)
    }

    override fun set(key: String, value: String) {
        val file = getFile(key)
        try {
            val destination = (file.sink()).buffer()
            destination.use {
                it.writeUtf8(value)
            }

        } catch (t: Throwable ) {
            Log.w(LOG_TAG, "set error", t)
        }
    }

    override fun get(key: String, fallback: String?): String? {
        val file = getFile(key)
        return if (file.exists()) {
            try {
                val source = file.source().buffer()
                source.use {
                    it.readUtf8()
                }

            } catch (t: Throwable ) {
                Log.w(LOG_TAG, "get error", t)
                fallback
            }

        } else {
            fallback
        }
    }

    override fun remove(key: String) {
        val file = getFile(key)
        file.delete()
    }
}