package data.repository.favourite

import domain.model.BaseResponse
import domain.utils.NetworkCall

interface FavouritesRepo {
    suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>>
}