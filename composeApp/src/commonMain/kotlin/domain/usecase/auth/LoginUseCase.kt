package domain.usecase.auth

import data.model.User
import data.repository.auth.AuthRepo
import domain.dto.LoginRequestDto
import domain.model.BaseResponse
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginUseCase(
    private val authRepository: AuthRepo,
) : SuspendUseCase<LoginUseCase.Params, Flow<NetworkCall<BaseResponse<User>>>> {

    data class Params(val requestDto: LoginRequestDto)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<User>>> {
        return flow {
            emit(authRepository.login(params.requestDto))
        }.flowOn(Dispatchers.IO)
    }
}
