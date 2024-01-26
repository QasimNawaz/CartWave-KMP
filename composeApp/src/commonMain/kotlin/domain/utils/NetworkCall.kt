package domain.utils

sealed class NetworkCall<out T : Any?> {

    data class Success<out T : Any?>(val data: T) :
        NetworkCall<T>()

    sealed class Error(val message: String?) :
        NetworkCall<Nothing>() {
        data class HttpError(
            val code: Int,
            val errorMessage: String?,
        ) : Error(message = errorMessage)

        data class SerializationError(
            val errorMessage: String?,
        ) : Error(message = errorMessage)

        data class GenericError(
            val errorMessage: String?,
        ) : Error(message = errorMessage)
    }
}