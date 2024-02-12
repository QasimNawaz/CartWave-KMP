package data.repository.address

import data.dataSource.RemoteDataSource
import domain.model.Address
import domain.model.BaseResponse
import domain.utils.NetworkCall

class AddressRepoImpl(
    private val remoteData: RemoteDataSource,
) : AddressRepo {
    override suspend fun addAddress(address: String): NetworkCall<BaseResponse<String>> {
        return remoteData.addAddress(address)
    }

    override suspend fun updatePrimaryAddress(addressId: Int): NetworkCall<BaseResponse<String>> {
        return remoteData.updatePrimaryAddress(addressId)
    }

    override suspend fun getPrimaryAddress(): NetworkCall<BaseResponse<Address>> {
        return remoteData.getPrimaryAddress()
    }

    override suspend fun getAddresses(): NetworkCall<BaseResponse<List<Address>>> {
        return remoteData.getAddresses()
    }
}