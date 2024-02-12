package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("id")
    val id: Int,
    @SerialName("userId")
    val userId: Int,
    @SerialName("address")
    val address: String,
    @SerialName("isPrimary")
    var isPrimary: Boolean
)