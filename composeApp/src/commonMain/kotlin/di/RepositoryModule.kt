package di

import data.dataSource.RemoteDataSource
import data.dataSource.RemoteDataSourceImpl
import data.repository.auth.AuthRepo
import data.repository.cache.KeyValueStorageRepo
import data.repository.auth.AuthRepoImpl
import data.repository.cache.KeyValueStorageRepoImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<KeyValueStorageRepo> { KeyValueStorageRepoImpl(get()) }
    single<AuthRepo> { AuthRepoImpl(get()) }
    single<RemoteDataSource> { RemoteDataSourceImpl(get(), get()) }
}