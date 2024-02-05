package data.dataSource.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import data.repository.cache.KeyValueStorageRepo
import data.utils.URLConstants.GET_FAVOURITES
import data.utils.safeRequest
import domain.model.PagingBaseResponse
import domain.model.Product
import domain.utils.NetworkCall
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType

class FavouritesPagingSource(
    private val client: HttpClient,
    private val keyValueStorageRepo: KeyValueStorageRepo
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val page = params.key ?: 1
            val response =
                client.safeRequest<PagingBaseResponse<List<Product>>> {
                    method = HttpMethod.Get
                    url(GET_FAVOURITES)
                    header(
                        "Authorization",
                        "Bearer ${keyValueStorageRepo.accessToken}"
                    )
                    parameter("pageNumber", page)
                    parameter("pageSize", 10)
                    parameter("userId", keyValueStorageRepo.user?.id)
                    contentType(ContentType.Application.Json)
                }
            when (response) {
                is NetworkCall.Error.GenericError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Error.HttpError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Error.SerializationError -> LoadResult.Error(Throwable(response.message))
                is NetworkCall.Success -> {
                    if (response.data.success == true) {
                        val favourites = response.data.data
                        LoadResult.Page(
                            data = favourites ?: emptyList(),
                            prevKey = if (page == 1) null else page.minus(1),
                            nextKey = if (response.data.data?.isEmpty() == true) null else page.plus(
                                1
                            ),
                        )
                    } else {
                        LoadResult.Error(Throwable(response.data.message))
                    }

                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}