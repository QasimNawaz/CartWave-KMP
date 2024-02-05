package data.repository.cart

import domain.model.BaseResponse
import domain.model.Product
import domain.utils.NetworkCall

interface CartRepo {
    suspend fun updateCart(
        productId: Int, cartQty: Int
    ): NetworkCall<BaseResponse<String>>

    suspend fun removeCart(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun getUserCart(): NetworkCall<BaseResponse<List<Product>>>
}