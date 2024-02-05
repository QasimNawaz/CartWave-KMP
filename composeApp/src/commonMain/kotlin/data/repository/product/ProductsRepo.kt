package data.repository.product

import domain.model.BaseResponse
import domain.model.Product
import domain.model.ProductsByCategoryItem
import domain.utils.NetworkCall

interface ProductsRepo {
    suspend fun getProductsGroupBySubCategory(category: String): NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>

    suspend fun getProductDetail(productId: Int): NetworkCall<BaseResponse<Product>>
}