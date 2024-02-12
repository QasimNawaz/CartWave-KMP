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

class GetPrimaryAddressUseCase(
    private val addressRepo: AddressRepo,
) : SuspendUseCase<Unit, Flow<NetworkCall<BaseResponse<Address>>>> {
    override suspend fun execute(params: Unit): Flow<NetworkCall<BaseResponse<Address>>> {
        return flow {
            emit(addressRepo.getPrimaryAddress())
        }.flowOn(Dispatchers.IO)
    }
}