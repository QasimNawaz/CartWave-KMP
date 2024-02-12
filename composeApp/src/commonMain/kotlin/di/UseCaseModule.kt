package di

import domain.usecase.address.AddAddressUseCase
import domain.usecase.address.GetAddressesUseCase
import domain.usecase.address.GetPrimaryAddressUseCase
import domain.usecase.address.UpdatePrimaryAddressUseCase
import domain.usecase.auth.LoginUseCase
import domain.usecase.auth.RegisterUseCase
import domain.usecase.cart.UpdateCartUseCase
import domain.usecase.cart.GetUserCartUseCase
import domain.usecase.cart.RemoveCartUseCase
import domain.usecase.favourite.AddToFavouriteUseCase
import domain.usecase.favourite.FavouritesPagingUseCase
import domain.usecase.favourite.RemoveFromFavouriteUseCase
import domain.usecase.order.PlaceOrderUseCase
import domain.usecase.product.ProductDetailUseCase
import domain.usecase.product.ProductsGroupBySubCategoryUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { ProductsGroupBySubCategoryUseCase(get()) }
    single { ProductDetailUseCase(get()) }
    single { AddToFavouriteUseCase(get()) }
    single { FavouritesPagingUseCase(get()) }
    single { RemoveFromFavouriteUseCase(get()) }
    single { UpdateCartUseCase(get()) }
    single { RemoveCartUseCase(get()) }
    single { GetUserCartUseCase(get()) }
    single { AddAddressUseCase(get()) }
    single { GetAddressesUseCase(get()) }
    single { GetPrimaryAddressUseCase(get()) }
    single { UpdatePrimaryAddressUseCase(get()) }
    single { PlaceOrderUseCase(get()) }
}