package data.dataSource

import data.model.User
import data.repository.cache.KeyValueStorageRepo
import data.utils.URLConstants
import data.utils.URLConstants.ADD_TO_CART
import data.utils.URLConstants.ADD_TO_FAVOURITE
import data.utils.URLConstants.GET_PRODUCT
import data.utils.URLConstants.GET_USER_CART
import data.utils.URLConstants.PRODUCTS_GROUP_BY_CATEGORY
import data.utils.URLConstants.REMOVE_FROM_CART
import data.utils.URLConstants.REMOVE_FROM_FAVOURITE
import data.utils.safeRequest
import domain.dto.UpdateCartRequestDto
import domain.dto.UpdateFavouriteRequestDto
import domain.dto.LoginRequestDto
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.model.Product
import domain.model.ProductsByCategoryItem
import domain.utils.NetworkCall
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import utils.Logger

class RemoteDataSourceImpl(
    private val client: HttpClient,
    private val keyValueStorageRepo: KeyValueStorageRepo
) : RemoteDataSource {
    override suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>> {
        Logger.d("register", "RemoteDataSourceImpl")
        return client.safeRequest<BaseResponse<User>> {
            method = HttpMethod.Post
            url(URLConstants.LOGIN)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }

    override suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>> {
        Logger.d("register", "RemoteDataSourceImpl")
        return client.safeRequest<BaseResponse<User>> {
            method = HttpMethod.Post
            url(URLConstants.REGISTER)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }

    override suspend fun getProductsGroupBySubCategory(category: String): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>> {
        Logger.d("getProductsGroupBySubCategory", "RemoteDataSourceImpl")
        return client.safeRequest<BaseResponse<List<ProductsByCategoryItem>>> {
            method = HttpMethod.Get
            url(PRODUCTS_GROUP_BY_CATEGORY)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            parameter("userId", keyValueStorageRepo.user?.id)
            parameter("category", category)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun getProductDetail(productId: Int): NetworkCall<BaseResponse<Product>> {
        return client.safeRequest {
            method = HttpMethod.Get
            url(GET_PRODUCT)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            parameter("userId", keyValueStorageRepo.user?.id)
            parameter("productId", productId)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        Logger.d("addToFavourite", "RemoteDataSourceImpl")
        return client.safeRequest {
            method = HttpMethod.Post
            url(ADD_TO_FAVOURITE)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(
                UpdateFavouriteRequestDto(
                    keyValueStorageRepo.user?.id ?: 0,
                    productId
                )
            )
        }
    }

    override suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        Logger.d("removeFromFavourite", "RemoteDataSourceImpl")
        return client.safeRequest {
            method = HttpMethod.Post
            url(REMOVE_FROM_FAVOURITE)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(
                UpdateFavouriteRequestDto(
                    keyValueStorageRepo.user?.id ?: 0,
                    productId
                )
            )
        }
    }

    override suspend fun getUserCart(): NetworkCall<BaseResponse<List<Product>>> {
        return client.safeRequest {
            method = HttpMethod.Get
            url(GET_USER_CART)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            parameter("userId", keyValueStorageRepo.user?.id)
            contentType(ContentType.Application.Json)
        }
    }

    override suspend fun addToCart(
        productId: Int,
        cartQty: Int
    ): NetworkCall<BaseResponse<String>> {
        return client.safeRequest {
            method = HttpMethod.Post
            url(ADD_TO_CART)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(
                UpdateCartRequestDto(
                    keyValueStorageRepo.user?.id ?: 0,
                    productId,
                    cartQty
                )
            )
        }
    }

    override suspend fun removeFromCart(productId: Int): NetworkCall<BaseResponse<String>> {
        return client.safeRequest {
            method = HttpMethod.Post
            url(REMOVE_FROM_CART)
            header("Authorization", "Bearer ${keyValueStorageRepo.accessToken}")
            contentType(ContentType.Application.Json)
            setBody(
                UpdateCartRequestDto(
                    keyValueStorageRepo.user?.id ?: 0,
                    productId,
                    0
                )
            )
        }
    }
}