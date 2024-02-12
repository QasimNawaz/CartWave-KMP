package data.repository.order

import domain.dto.PlaceOrderRequestDto
import domain.model.BaseResponse
import domain.utils.NetworkCall

interface OrderRepo {
    suspend fun placeOrder(placeOrderRequestDto: PlaceOrderRequestDto): NetworkCall<BaseResponse<String>>
}