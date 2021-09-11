package com.example.commons

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents only one value.
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class SingleValue<out T>(private val content: T) {

    private var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content

    override fun equals(other: Any?): Boolean {
        return if (other is SingleValue<*>) {
            other.content == this.content
        } else {
            false
        }
    }

    override fun hashCode(): Int {
        return content.hashCode()
    }

    override fun toString(): String {
        return content.toString()
    }
}

/**
 * An [Observer] for [SingleValue], simplifying the pattern of checking if the [SingleValue]'s content has
 * already been handled.
 *
 * [onEventUnhandledContent] is *only* called if the [SingleValue]'s contents has not been handled.
 */
inline fun <T> LiveData<SingleValue<T>>.observeSingleValue(
        owner: LifecycleOwner,
        crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner, Observer { it?.getContentIfNotHandled()?.let(onEventUnhandledContent) })
}

inline fun <T> MutableLiveData<SingleValue<T>>.setSingleValue(value: T) {
    this.value = SingleValue<T>(value)
}
