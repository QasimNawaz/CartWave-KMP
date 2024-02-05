package data.repository.favourite

import androidx.paging.PagingData
import domain.model.BaseResponse
import domain.model.Product
import domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface FavouritesRepo {
    suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>>

    fun getFavouritesPaging(): Flow<PagingData<Product>>
}