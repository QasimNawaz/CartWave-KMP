package domain.dto

@kotlinx.serialization.Serializable
data class UpdateCartRequestDto(
    val userId: Int,
    val productId: Int,
    val cartQty: Int
)