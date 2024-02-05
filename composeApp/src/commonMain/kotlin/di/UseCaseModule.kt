package di

import domain.usecase.auth.LoginUseCase
import domain.usecase.auth.RegisterUseCase
import domain.usecase.cart.UpdateCartUseCase
import domain.usecase.cart.GetUserCartUseCase
import domain.usecase.cart.RemoveCartUseCase
import domain.usecase.favourite.AddToFavouriteUseCase
import domain.usecase.favourite.RemoveFromFavouriteUseCase
import domain.usecase.product.ProductDetailUseCase
import domain.usecase.product.ProductsGroupBySubCategoryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { ProductsGroupBySubCategoryUseCase(get()) }
    single { ProductDetailUseCase(get()) }
    single { AddToFavouriteUseCase(get()) }
    single { RemoveFromFavouriteUseCase(get()) }
    single { UpdateCartUseCase(get()) }
    single { RemoveCartUseCase(get()) }
    single { GetUserCartUseCase(get()) }
}