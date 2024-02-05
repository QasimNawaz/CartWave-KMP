package domain.usecase.cart

import data.repository.cart.CartRepo
import domain.model.BaseResponse
import domain.model.Product
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetUserCartUseCase(
    private val cartRepo: CartRepo,
) : SuspendUseCase<Unit, Flow<NetworkCall<BaseResponse<List<Product>>>>> {
    override suspend fun execute(params: Unit): Flow<NetworkCall<BaseResponse<List<Product>>>> {
        return flow {
            emit(cartRepo.getUserCart())
        }.flowOn(Dispatchers.IO)
    }


}