package domain.usecase.base

interface UseCase<in Params, out T> {
    fun execute(params: Params): T
}