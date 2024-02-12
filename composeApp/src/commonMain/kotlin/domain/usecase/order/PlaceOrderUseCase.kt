package domain.usecase.order

import data.repository.order.OrderRepo
import domain.dto.PlaceOrderRequestDto
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlaceOrderUseCase(
    private val orderRepo: OrderRepo,
) : SuspendUseCase<PlaceOrderUseCase.Params, Flow<NetworkCall<BaseResponse<String>>>> {

    data class Params(val placeOrderRequestDto: PlaceOrderRequestDto)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(orderRepo.placeOrder(params.placeOrderRequestDto))
        }.flowOn(Dispatchers.IO)
    }
}