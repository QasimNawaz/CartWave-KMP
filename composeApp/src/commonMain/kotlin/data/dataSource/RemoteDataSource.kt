package data.dataSource

import data.model.User
import domain.dto.LoginRequestDto
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.model.Product
import domain.model.ProductsByCategoryItem
import domain.utils.NetworkCall

interface RemoteDataSource {
    suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun getProductsGroupBySubCategory(category: String): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>

    suspend fun getProductDetail(productId: Int): NetworkCall<BaseResponse<Product>>
    suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun getUserCart(): NetworkCall<BaseResponse<List<Product>>>
    suspend fun addToCart(productId: Int, cartQty: Int): NetworkCall<BaseResponse<String>>

    suspend fun removeFromCart(productId: Int): NetworkCall<BaseResponse<String>>
}