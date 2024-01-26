package domain.dto

@kotlinx.serialization.Serializable
data class RegisterRequestDto(
    val firstName: String? = null,
    val lastName: String? = null,
    val avatar: String? = null,
    val email: String? = null,
    val password: String? = null
)
