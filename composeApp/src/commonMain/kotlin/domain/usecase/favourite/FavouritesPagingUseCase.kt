package domain.usecase.favourite

import androidx.paging.PagingData
import data.repository.favourite.FavouritesRepo
import domain.model.Product
import domain.usecase.base.UseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class FavouritesPagingUseCase(
    private val favouritesRepo: FavouritesRepo,
) : UseCase<Unit, Flow<PagingData<Product>>> {
    override fun execute(params: Unit): Flow<PagingData<Product>> {
        return favouritesRepo.getFavouritesPaging().flowOn(Dispatchers.IO)
    }


}