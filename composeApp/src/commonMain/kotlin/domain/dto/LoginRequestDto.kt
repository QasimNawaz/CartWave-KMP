package domain.dto

@kotlinx.serialization.Serializable
data class LoginRequestDto(
    val email: String? = null,
    val password: String? = null
)
