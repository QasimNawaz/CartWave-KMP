package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val `data`: T?,
    @SerialName("message")
    val message: String
)