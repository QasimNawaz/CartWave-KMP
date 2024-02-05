package di

import data.dataSource.RemoteDataSource
import data.dataSource.RemoteDataSourceImpl
import data.repository.auth.AuthRepo
import data.repository.cache.KeyValueStorageRepo
import data.repository.auth.AuthRepoImpl
import data.repository.cache.KeyValueStorageRepoImpl
import data.repository.cart.CartRepo
import data.repository.cart.CartRepoImpl
import data.repository.favourite.FavouritesRepo
import data.repository.favourite.FavouritesRepoImpl
import data.repository.product.ProductsRepo
import data.repository.product.ProductsRepoImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<KeyValueStorageRepo> { KeyValueStorageRepoImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get(), get()) }
    single<AuthRepo> { AuthRepoImpl(get()) }
    single<ProductsRepo> { ProductsRepoImpl(get()) }
    single<FavouritesRepo> { FavouritesRepoImpl(get()) }
    single<CartRepo> { CartRepoImpl(get()) }
}