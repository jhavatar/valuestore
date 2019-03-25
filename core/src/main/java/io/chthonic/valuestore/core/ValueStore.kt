package io.chthonic.valuestore.core

/**
 * Created by jhavatar on 2/17/2019.
 */

interface ValueStore<T> {
    fun set(key: String, value: T)

    fun get(key: String, fallback: T?): T?

    fun remove(key: String)
}