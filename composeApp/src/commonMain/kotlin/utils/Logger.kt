package utils

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

object Logger {
    private const val TAG = "Logger"

    init {
        Napier.base(DebugAntilog(defaultTag = TAG))
    }

    fun v(message: String, tag: String? = null) {
        Napier.v(message = message, tag = tag, throwable = null)
    }

    fun i(message: String, tag: String? = null) {
        Napier.i(message = message, tag = tag, throwable = null)
    }

    fun e(message: String, tag: String? = null) {
        Napier.e(message = message, tag = tag, throwable = null)
    }

    fun d(message: String, tag: String? = null) {
        Napier.d(message = message, tag = tag, throwable = null)
    }
}