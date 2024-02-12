package domain.dto

@kotlinx.serialization.Serializable
data class AddAddressRequestDto(
    val userId: Int,
    val address: String,
)