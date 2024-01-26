package data.repository.auth

import data.dataSource.RemoteDataSource
import data.model.User
import domain.dto.LoginRequestDto
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.utils.NetworkCall

class AuthRepoImpl(
    private val remoteData: RemoteDataSource,
) : AuthRepo {
    override suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>> {
        return remoteData.login(requestDto)
    }

    override suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>> {
        return remoteData.register(requestDto)
    }
}