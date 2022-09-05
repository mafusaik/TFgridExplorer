package by.homework.hlazarseni.tfgridexplorer.entity

sealed class PagingData<out T> {

    data class Item<T>(val data: T): PagingData<T>()

    object Loading: PagingData<Nothing>()
    object Error: PagingData<Nothing>()

}