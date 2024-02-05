package data.repository.cart

import data.dataSource.RemoteDataSource
import domain.model.BaseResponse
import domain.model.Product
import domain.utils.NetworkCall

class CartRepoImpl(
    private val remoteData: RemoteDataSource,
) : CartRepo {
    override suspend fun updateCart(
        productId: Int,
        cartQty: Int
    ): NetworkCall<BaseResponse<String>> {
        return remoteData.addToCart(productId, cartQty)
    }

    override suspend fun removeCart(productId: Int): NetworkCall<BaseResponse<String>> {
        return remoteData.removeFromCart(productId)
    }

    override suspend fun getUserCart(): NetworkCall<BaseResponse<List<Product>>> {
        return remoteData.getUserCart()
    }
}