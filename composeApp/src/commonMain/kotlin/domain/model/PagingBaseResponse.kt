package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagingBaseResponse<T>(
    @SerialName("success")
    val success: Boolean?,
    @SerialName("data")
    val data: T?,
    @SerialName("message")
    val message: String?,
    @SerialName("pageNumber")
    val pageNumber: Int?,
    @SerialName("pageSize")
    val pageSize: Int?,
    @SerialName("totalCount")
    val totalCount: Int?
)