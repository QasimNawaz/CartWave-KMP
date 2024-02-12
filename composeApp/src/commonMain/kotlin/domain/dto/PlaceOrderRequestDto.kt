package domain.dto

@kotlinx.serialization.Serializable
data class PlaceOrderRequestDto(
    val userId: Int = 0,
    val orderDate: String,
    val shippingAddress: String,
    val promoCode: String,
    val orderStatus: String,
    val totalAmount: Int,
    val paymentMethod: String,
    val products: List<OrderProduct>
)

@kotlinx.serialization.Serializable
data class OrderProduct(
    val productId: Int,
    val title: String,
    val price: Int,
    val quantity: Int
)