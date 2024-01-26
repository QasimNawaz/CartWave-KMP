package data.utils

import domain.utils.HttpExceptions
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import platform.httpClient

private const val TIME_OUT = 60_000L

val ktorHttpClient = httpClient {
    expectSuccess = true
    engine {
        pipelining = true
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(HttpTimeout) {
        requestTimeoutMillis = TIME_OUT
        connectTimeoutMillis = TIME_OUT
        socketTimeoutMillis = TIME_OUT
    }

//    install(Logging) {
//        logger = object : Logger {
//            override fun log(message: String) {
//                utils.Logger.d(message, "HTTP Client ->")
//            }
//        }
//        level = LogLevel.ALL
//    }


    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

//    install(ResponseObserver) {
//        onResponse { response ->
//            utils.Logger.d("${response.status.value}", "Http status")
//        }
//    }

    HttpResponseValidator {
        validateResponse { response ->
            if (!response.status.isSuccess()) {
                val failureReason = when (response.status) {
                    HttpStatusCode.Unauthorized -> "Unauthorized request"
                    HttpStatusCode.Forbidden -> "${response.status.value} Missing API key."
                    HttpStatusCode.NotFound -> "Invalid Request"
                    HttpStatusCode.UpgradeRequired -> "Upgrade to VIP"
                    HttpStatusCode.RequestTimeout -> "Network Timeout"
                    in HttpStatusCode.InternalServerError..HttpStatusCode.GatewayTimeout -> "${response.status.value} Server Error"

                    else -> "Network error!"
                }

                throw HttpExceptions(
                    response = response,
                    failureReason = failureReason,
                    cachedResponseText = response.bodyAsText(),
                )
            }
        }
    }
}