package by.homework.hlazarseni.tfgridexplorer.presentation.model

sealed class Lce<out T>{
    object Loading : Lce<Nothing>()
    data class Content<T>(val data: T): Lce<T>()
    data class Error(val throwable: Throwable): Lce<Nothing>()

}