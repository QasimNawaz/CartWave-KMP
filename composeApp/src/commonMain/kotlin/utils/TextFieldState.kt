package utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

open class TextFieldState(
    private val validator: (String) -> Boolean = { true },
    private val errorMessage: (String) -> String,
    private val defaultText: String = "",
) {
    var text by mutableStateOf(defaultText)
    var error by mutableStateOf<String?>(null)

    fun validate() {
        error = if (validator(text)) {
            null
        } else {
            errorMessage(text)
        }
    }
}