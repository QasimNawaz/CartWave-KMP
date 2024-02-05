package domain.model


import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("actualPrice")
    val actualPrice: String?,
    @SerialName("averageRating")
    val averageRating: String?,
    @SerialName("brand")
    val brand: String?,
    @SerialName("category")
    val category: String?,
    @SerialName("crawledAt")
    val crawledAt: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("discount")
    val discount: String?,
    @SerialName("id")
    val id: Int = -1,
    @SerialName("images")
    val images: List<String> = emptyList(),
    @SerialName("isFavourite")
    var isFavourite: Boolean = false,
    @SerialName("outOfStock")
    val outOfStock: Boolean?,
    @SerialName("pid")
    val pid: String?,
    @SerialName("productDetails")
    val productDetails: List<Map<String, String>>?,
    @SerialName("seller")
    val seller: String?,
    @SerialName("sellingPrice")
    val sellingPrice: String?,
    @SerialName("subCategory")
    val subCategory: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("url")
    val url: String?,
    @SerialName("cartQty")
    val cartQty: Int = 0,
)