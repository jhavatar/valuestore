[![](https://jitpack.io/v/jhavatar/valuestore.svg)](https://jitpack.io/#jhavatar/valuestore)

# valuestore
Simple interface for storing values. 

```kotlin
interface ValueStore<T> {
    fun set(key: String, value: T)

    fun get(key: String, fallback: T?): T?

    fun remove(key: String)
}
```

## stringstore
An extension of the ValueStore interface focused on only storing Strings. It is ideal for json storage. 

```kotlin
interface StringStore: ValueStore<String>
```

It comes with implementations for storing to shared preferences, internal storage and internal cache.

### Setup
#### 1. Include the library

Add in your root build.gradle

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency

 ```groovy
 dependencies {
     implementation 'com.github.jhavatar.valuestore:stringstore:1.0.4'
 }
 ```
 
 
 ## Changelog
 
 v1.0.4 
- initial release
