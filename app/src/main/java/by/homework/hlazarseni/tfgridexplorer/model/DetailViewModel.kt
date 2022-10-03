package by.homework.hlazarseni.tfgridexplorer.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.homework.hlazarseni.tfgridexplorer.entity.DetailNode
import by.homework.hlazarseni.tfgridexplorer.entity.PagingData
import by.homework.hlazarseni.tfgridexplorer.lce.Lce
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(private val node: DetailNode): ViewModel() {

    private val _lceFlow = MutableStateFlow<Lce<DetailNode>>(
       Lce.Loading
    )

        fun onButtonClicked() {
        viewModelScope.launch {
            _lceFlow.tryEmit(Lce.Loading)
            delay(1000)
            _lceFlow.tryEmit(Lce.Content(node))
        }
    }

    val lceState = flow {
        val lce = runCatching {
          //  dataSource.getData()
        }
            .fold(
                onSuccess = {
                   PagingData.Item(it)
                },
                onFailure = {
                   PagingData.Error(it)
                }
            )
        emit(lce)
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue = Lce.Loading
    )
}