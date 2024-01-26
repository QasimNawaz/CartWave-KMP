package domain.usecase.auth

import data.model.User
import data.repository.auth.AuthRepo
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterUseCase(
    private val authRepository: AuthRepo,
) : SuspendUseCase<RegisterUseCase.Params, Flow<NetworkCall<BaseResponse<User>>>> {

    data class Params(val requestDto: RegisterRequestDto)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<User>>> {
        return flow {
            emit(authRepository.register(params.requestDto))
        }.flowOn(Dispatchers.IO)
    }
}