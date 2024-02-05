package data.repository.product

import data.dataSource.RemoteDataSource
import domain.model.BaseResponse
import domain.model.Product
import domain.model.ProductsByCategoryItem
import domain.utils.NetworkCall

class ProductsRepoImpl(
    private val remoteData: RemoteDataSource,
) : ProductsRepo {
    override suspend fun getProductsGroupBySubCategory(category: String): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>> {
        return remoteData.getProductsGroupBySubCategory(category)
    }

    override suspend fun getProductDetail(productId: Int): NetworkCall<BaseResponse<Product>> {
        return remoteData.getProductDetail(productId)
    }
}