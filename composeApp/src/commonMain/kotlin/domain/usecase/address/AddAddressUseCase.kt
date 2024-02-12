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

class AddAddressUseCase(
    private val addressRepo: AddressRepo,
) : SuspendUseCase<AddAddressUseCase.Params, Flow<NetworkCall<BaseResponse<String>>>> {

    data class Params(val address: String)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<String>>> {
        return flow {
            emit(addressRepo.addAddress(params.address))
        }.flowOn(Dispatchers.IO)
    }
}