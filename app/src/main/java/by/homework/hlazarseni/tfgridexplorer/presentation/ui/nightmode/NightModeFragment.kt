package by.homework.hlazarseni.tfgridexplorer.presentation.ui.nightmode
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import by.homework.hlazarseni.tfgridexplorer.databinding.NightModeFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.domain.model.NightMode
import by.homework.hlazarseni.tfgridexplorer.presentation.extensions.applyHorizontalWindowInsets
import by.homework.hlazarseni.tfgridexplorer.presentation.extensions.applyWindowInsets
import org.koin.androidx.viewmodel.ext.android.viewModel

class NightModeFragment : Fragment() {

    private var _binding: NightModeFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val modeViewModel by viewModel<NightModeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return NightModeFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.applyWindowInsets()
            toolbar.setupWithNavController(findNavController())
            radioGroup.applyHorizontalWindowInsets()

            when (modeViewModel.selectedNightMode) {
                NightMode.DARK -> buttonDarkMode
                NightMode.LIGHT -> buttonLightMode
                NightMode.SYSTEM -> buttonSystemMode
            }.isChecked = true

            radioGroup.setOnCheckedChangeListener { _, buttonId ->
                val (prefsMode, systemMode) = when (buttonId) {
                    buttonDarkMode.id -> NightMode.DARK to AppCompatDelegate.MODE_NIGHT_YES
                    buttonLightMode.id -> NightMode.LIGHT to AppCompatDelegate.MODE_NIGHT_NO
                    buttonSystemMode.id -> NightMode.SYSTEM to AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    else -> error("incorrect buttonId $buttonId")
                }

                modeViewModel.selectedNightMode = prefsMode
                AppCompatDelegate.setDefaultNightMode(systemMode)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}