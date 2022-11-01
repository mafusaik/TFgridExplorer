package by.homework.hlazarseni.tfgridexplorer.presentation.ui.nightmode
import androidx.lifecycle.ViewModel
import by.homework.hlazarseni.tfgridexplorer.domain.service.NightModeService

class NightModeViewModel(private val prefsService: NightModeService) : ViewModel() {

    var selectedNightMode by prefsService::nightMode
}