package di

import data.utils.ktorHttpClient
import org.koin.dsl.module

val appModule = module {
    includes(keyValueSettingsModule, repositoryModule, useCaseModule)
    single { ktorHttpClient }
}