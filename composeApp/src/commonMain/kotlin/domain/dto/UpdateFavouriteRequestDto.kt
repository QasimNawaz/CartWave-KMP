package domain.dto

@kotlinx.serialization.Serializable
data class UpdateFavouriteRequestDto(
    val userId: Int,
    val productId: Int,
)