package di

import domain.usecase.auth.LoginUseCase
import domain.usecase.auth.RegisterUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
}