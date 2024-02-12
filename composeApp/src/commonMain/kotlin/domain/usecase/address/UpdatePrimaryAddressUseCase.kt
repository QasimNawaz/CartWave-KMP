package domain.usecase.address

import data.repository.address.AddressRepo
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UpdatePrimaryAddressUseCase(
    private val addressRepo: AddressRepo,
) : SuspendUseCase<UpdatePrimaryAddressUseCase.Params, Flow<NetworkCall<BaseResponse<String>>>> {
    data class Params(val addressId: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(addressRepo.updatePrimaryAddress(params.addressId))
        }.flowOn(Dispatchers.IO)
    }
}