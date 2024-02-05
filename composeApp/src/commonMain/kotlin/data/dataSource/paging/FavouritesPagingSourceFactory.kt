package data.dataSource.paging

import androidx.paging.PagingSource
import data.repository.cache.KeyValueStorageRepo
import domain.model.Product
import io.ktor.client.HttpClient

class FavouritesPagingSourceFactory(
    private val client: HttpClient,
    private val keyValueStorageRepo: KeyValueStorageRepo
) : () -> PagingSource<Int, Product> {
    override fun invoke(): PagingSource<Int, Product> {
        return FavouritesPagingSource(client, keyValueStorageRepo)
    }
}