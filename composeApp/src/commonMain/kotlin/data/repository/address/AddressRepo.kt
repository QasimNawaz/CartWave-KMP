package data.repository.address

import domain.model.Address
import domain.model.BaseResponse
import domain.utils.NetworkCall

interface AddressRepo {

    suspend fun addAddress(address: String): NetworkCall<BaseResponse<String>>

    suspend fun updatePrimaryAddress(addressId: Int): NetworkCall<BaseResponse<String>>

    suspend fun getPrimaryAddress(): NetworkCall<BaseResponse<Address>>

    suspend fun getAddresses(): NetworkCall<BaseResponse<List<Address>>>
}