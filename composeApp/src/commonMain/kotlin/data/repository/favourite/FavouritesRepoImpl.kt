package data.repository.favourite

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import data.dataSource.RemoteDataSource
import data.dataSource.paging.FavouritesPagingSourceFactory
import domain.model.BaseResponse
import domain.model.Product
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FavouritesRepoImpl(
    private val remoteData: RemoteDataSource,
    private val favouritesPagingSourceFactory: FavouritesPagingSourceFactory
) : FavouritesRepo {
    override suspend fun addToFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        return remoteData.addToFavourite(productId)
    }

    override suspend fun removeFromFavourite(productId: Int): NetworkCall<BaseResponse<String>> {
        return remoteData.removeFromFavourite(productId)
    }

    override fun getFavouritesPaging(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = favouritesPagingSourceFactory
        ).flow.flowOn(Dispatchers.IO)
    }
}