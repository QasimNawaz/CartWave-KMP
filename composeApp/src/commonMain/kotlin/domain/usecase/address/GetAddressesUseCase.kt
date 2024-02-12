package domain.usecase.address

import data.repository.address.AddressRepo
import domain.model.Address
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetAddressesUseCase(
    private val addressRepo: AddressRepo,
) : SuspendUseCase<Unit, Flow<NetworkCall<BaseResponse<List<Address>>>>> {

    override suspend fun execute(params: Unit): Flow<NetworkCall<BaseResponse<List<Address>>>> {
        return flow {
            emit(addressRepo.getAddresses())
        }.flowOn(Dispatchers.IO)
    }
}