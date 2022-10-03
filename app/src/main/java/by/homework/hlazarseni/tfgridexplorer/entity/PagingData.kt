package by.homework.hlazarseni.tfgridexplorer.entity

open class PagingData<out T> {

    data class Item<T>(val data: T): PagingData<T>()
    object Loading: PagingData<Nothing>()
    data class Error(val throwable: Throwable): PagingData<Nothing>()
}
