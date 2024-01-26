package data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("authToken")
    val authToken: String?,
    @SerialName("avatar")
    val avatar: String?,
    @SerialName("createdAt")
    val createdAt: String?,
    @SerialName("email")
    val email: String?,
    @SerialName("firstName")
    val firstName: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("lastName")
    val lastName: String?
)
