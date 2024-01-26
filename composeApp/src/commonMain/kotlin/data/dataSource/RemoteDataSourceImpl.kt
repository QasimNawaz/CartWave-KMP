package data.dataSource

import data.model.User
import data.repository.cache.KeyValueStorageRepo
import data.utils.URLConstants
import data.utils.safeRequest
import domain.dto.LoginRequestDto
import domain.dto.RegisterRequestDto
import domain.model.BaseResponse
import domain.utils.NetworkCall
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import utils.Logger

class RemoteDataSourceImpl (
    private val client: HttpClient,
    private val keyValueStorageRepo: KeyValueStorageRepo
): RemoteDataSource {
    override suspend fun login(requestDto: LoginRequestDto): NetworkCall<BaseResponse<User>> {
        Logger.d("register", "RemoteDataSourceImpl")
        return client.safeRequest<BaseResponse<User>> {
            method = HttpMethod.Post
            url(URLConstants.LOGIN)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }

    override suspend fun register(requestDto: RegisterRequestDto): NetworkCall<BaseResponse<User>> {
        Logger.d("register", "RemoteDataSourceImpl")
        return client.safeRequest<BaseResponse<User>> {
            method = HttpMethod.Post
            url(URLConstants.REGISTER)
            contentType(ContentType.Application.Json)
            setBody(requestDto)
        }
    }
}