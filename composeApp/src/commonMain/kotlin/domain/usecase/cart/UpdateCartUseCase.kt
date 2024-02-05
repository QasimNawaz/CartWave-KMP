package domain.usecase.cart

import data.repository.cart.CartRepo
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdateCartUseCase(
    private val cartRepo: CartRepo,
) : SuspendUseCase<UpdateCartUseCase.Params, Flow<NetworkCall<BaseResponse<String>>>> {
    data class Params(val productId: Int, val cartQty: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(cartRepo.updateCart(params.productId, params.cartQty))
        }.flowOn(Dispatchers.IO)
    }


}