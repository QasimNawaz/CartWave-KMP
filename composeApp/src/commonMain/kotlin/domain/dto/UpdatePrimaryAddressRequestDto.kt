package domain.dto

@kotlinx.serialization.Serializable
data class UpdatePrimaryAddressRequestDto(
    val userId: Int,
    val addressId: Int,
)