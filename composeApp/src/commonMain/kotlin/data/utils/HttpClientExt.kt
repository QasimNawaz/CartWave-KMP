package data.utils

import domain.utils.HttpExceptions
import domain.utils.NetworkCall
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import kotlinx.serialization.SerializationException
import utils.Logger

suspend inline fun <reified T> HttpClient.safeRequest(
    crossinline block: HttpRequestBuilder.() -> Unit,
): NetworkCall<T> {
    return try {
        Logger.d("safeRequest")
        val response = request { block() }
        Logger.d("response: $response", "safeRequest")
        NetworkCall.Success(data = response.body())
    } catch (exception: ClientRequestException) {
        Logger.d("ClientRequestException", "safeRequest")
        NetworkCall.Error.HttpError(
            code = exception.response.status.value, errorMessage = exception.message
        )
    } catch (exception: HttpExceptions) {
        Logger.d("HttpExceptions: ${exception.message}", "safeRequest")
        NetworkCall.Error.HttpError(
            code = exception.response.status.value,
            errorMessage = exception.message,
        )
    } catch (exception: SerializationException) {
        Logger.d("SerializationException: ${exception.message}", "safeRequest")
        NetworkCall.Error.SerializationError(
            errorMessage = "${exception.message}",
        )
    } catch (exception: Exception) {
        Logger.d("Exception: ${exception.message}", "safeRequest")
        NetworkCall.Error.GenericError(
            errorMessage = "${exception.message}",
        )
    }

}