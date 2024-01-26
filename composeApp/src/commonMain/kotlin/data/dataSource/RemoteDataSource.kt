package data.dataSource

import data.model.User
import domain.dto.LoginRequestDto
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.utils.NetworkCall

interface RemoteDataSource {
    suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>>

    suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>>
}