package domain.usecase.favourite

import data.repository.favourite.FavouritesRepo
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddToFavouriteUseCase(
    private val favouritesRepo: FavouritesRepo,
) : SuspendUseCase<AddToFavouriteUseCase.Params, Flow<NetworkCall<BaseResponse<String>>>> {
    data class Params(val productId: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(favouritesRepo.addToFavourite(params.productId))
        }.flowOn(Dispatchers.IO)
    }


}