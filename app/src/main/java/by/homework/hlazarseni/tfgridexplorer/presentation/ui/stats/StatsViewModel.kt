package by.homework.hlazarseni.tfgridexplorer.presentation.ui.stats

import androidx.lifecycle.ViewModel
import by.homework.hlazarseni.tfgridexplorer.data.repository.NodeRepositoryImpl
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import kotlinx.coroutines.flow.*

class StatsViewModel(
    private val nodeRepositoryImpl: NodeRepositoryImpl
) : ViewModel() {

    val dataStatsFlow = flow {
        val state = nodeRepositoryImpl.getStats()
            .fold(
                onSuccess = { Lce.Content(it) },
                onFailure = { Lce.Error(it) }
            )
        emit(state)
    }
}