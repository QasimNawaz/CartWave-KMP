package domain.usecase.product

import data.repository.product.ProductsRepo
import domain.model.BaseResponse
import domain.model.ProductsByCategoryItem
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductsGroupBySubCategoryUseCase(
    private val productsRepository: ProductsRepo,
) : SuspendUseCase<ProductsGroupBySubCategoryUseCase.Params, Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>>> {
    data class Params(val category: String)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<List<ProductsByCategoryItem>>>> {
        return flow {
            emit(productsRepository.getProductsGroupBySubCategory(params.category))
        }.flowOn(Dispatchers.IO)
    }


}