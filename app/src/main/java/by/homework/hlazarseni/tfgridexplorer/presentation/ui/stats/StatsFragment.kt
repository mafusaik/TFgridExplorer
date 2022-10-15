package by.homework.hlazarseni.tfgridexplorer.presentation.ui.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.homework.hlazarseni.tfgridexplorer.databinding.StatsFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject


class StatsFragment : Fragment() {
    private var _binding: StatsFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val viewModel by inject<StatsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return StatsFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            viewModel.dataStatsFlow
                .onEach {
                    when (it) {
                        is Lce.Loading -> {

                        }
                        is Lce.Content -> {
                            nodesOnline.text = it.data.nodes
                            countries.text = it.data.countries
                            farms.text = it.data.farms
                        }
                        is Lce.Error -> {
                            Snackbar.make(
                                view,
                                it.throwable.message ?: "Error",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                    }
                }
                .launchIn(viewLifecycleOwner.lifecycleScope)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}