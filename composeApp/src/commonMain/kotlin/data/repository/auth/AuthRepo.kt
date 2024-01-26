package data.repository.auth

import data.model.User
import domain.dto.LoginRequestDto
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.utils.NetworkCall
import kotlinx.coroutines.flow.Flow

interface AuthRepo {
    suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>>
    suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>>
}