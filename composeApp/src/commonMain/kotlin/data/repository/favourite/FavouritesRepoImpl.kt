package data.repository.favourite

import data.dataSource.RemoteDataSource
import domain.model.BaseResponse
import domain.utils.NetworkCall

class FavouritesRepoImpl(
    private val remoteData: RemoteDataSource,
) : FavouritesRepo {
    override suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        return remoteData.addToFavourite(productId)
    }

    override suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        return remoteData.removeFromFavourite(productId)
    }
}