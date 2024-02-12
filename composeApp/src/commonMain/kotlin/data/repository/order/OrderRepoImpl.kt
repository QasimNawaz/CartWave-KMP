package data.repository.order

import data.dataSource.RemoteDataSource
import domain.dto.PlaceOrderRequestDto
import domain.model.BaseResponse
import domain.utils.NetworkCall

class OrderRepoImpl(
    private val remoteData: RemoteDataSource,
) : OrderRepo {
    override suspend fun placeOrder(placeOrderRequestDto: PlaceOrderRequestDto): NetworkCall<BaseResponse<String>> {
        return remoteData.placeOrder(placeOrderRequestDto)
    }
}