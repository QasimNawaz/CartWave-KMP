package domain.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductsByCategoryItem(
    @SerialName("category")
    val category: String?,
    @SerialName("products")
    val products: List<Product>?
)