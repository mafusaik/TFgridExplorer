package by.homework.hlazarseni.tfgridexplorer.domain.model

open class PagingData<out T> {

    data class Item<T>(val data: T): PagingData<T>()
    object Loading: PagingData<Nothing>()
    data class Error(val throwable: Throwable): PagingData<Nothing>()
}
