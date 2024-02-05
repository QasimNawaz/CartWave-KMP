package domain.usecase.product

import data.repository.product.ProductsRepo
import domain.model.BaseResponse
import domain.model.Product
import domain.usecase.base.SuspendUseCase
import domain.utils.NetworkCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ProductDetailUseCase(
    private val productsRepository: ProductsRepo,
) : SuspendUseCase<ProductDetailUseCase.Params, Flow<NetworkCall<BaseResponse<Product>>>> {

    data class Params(val productId: Int)

    override suspend fun execute(params: Params): Flow<NetworkCall<BaseResponse<Product>>> {
        return flow {
            emit(productsRepository.getProductDetail(params.productId))
        }.flowOn(Dispatchers.IO)
    }


}