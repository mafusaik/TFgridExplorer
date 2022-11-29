package by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import by.homework.hlazarseni.tfgridexplorer.R
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.constants.DetailConverterConstants.CONVERTER_HRU
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.constants.DetailConverterConstants.CONVERTER_MRU
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.constants.DetailConverterConstants.CONVERTER_SRU
import by.homework.hlazarseni.tfgridexplorer.databinding.NodeDetailFragmentBinding
import by.homework.hlazarseni.tfgridexplorer.presentation.model.Lce
import by.homework.hlazarseni.tfgridexplorer.presentation.ui.detail.util.TimeConverter.timeToString
import coil.load
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class NodeDetailFragment : Fragment() {
    private var _binding: NodeDetailFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<NodeDetailFragmentArgs>()

    private val viewModel by viewModel<DetailViewModel> { parametersOf(args.node) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return NodeDetailFragmentBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            viewModel.dataDetailFlow
                .onEach {
                    when (it) {
                        is Lce.Loading -> {
                            progress.isVisible = true
                        }
                        is Lce.Content -> {
                            toolbarDetail.setupWithNavController(findNavController())
                            detailImage.load(R.drawable.ic_fordetailnode)

                            val totalCru = args.node.totalResources.cru.toInt()
                            val usedCru = args.node.usedResources.cru.toInt()
                            val totalSru = (args.node.totalResources.sru / CONVERTER_SRU).toDouble()
                            val usedSru = (args.node.usedResources.sru / CONVERTER_SRU).toDouble()
                            val totalHru = (args.node.totalResources.hru / CONVERTER_HRU).toDouble()
                            val usedHru = (args.node.usedResources.hru / CONVERTER_HRU).toDouble()
                            val totalMru = (args.node.totalResources.mru / CONVERTER_MRU).toDouble()
                            val usedMru = (args.node.usedResources.mru / CONVERTER_MRU).toDouble()

                            progressCru.max = totalCru
                            progressCru.progress = usedCru
                            progressSru.max = totalSru.toInt()
                            progressSru.progress = usedSru.toInt()
                            progressHru.max = totalHru.toInt()
                            progressHru.progress = usedHru.toInt()
                            progressMru.max = totalMru.toInt()
                            progressMru.progress = usedMru.toInt()

                            percentCruText.text =
                                calculatePercent(usedCru.toDouble(), totalCru.toDouble())
                            percentSruText.text = calculatePercent(usedSru, totalSru)
                            percentHruText.text = calculatePercent(usedHru, totalHru)
                            percentMruText.text = calculatePercent(usedMru, totalMru)

                            val upTime = timeToString(args.node.uptime.toLong())
                            uptimeTextView.text = upTime

                            idTextView.text =
                                getString(R.string.id, args.node.nodeId)
                            farmIdTextView.text =
                                getString(R.string.farm_id, args.node.farmId)
                            cruTextView.text =
                                getString(R.string.cpu_resource, totalCru)
                            sruTextView.text =
                                getString(R.string.sru_resource, totalSru)
                            hruTextView.text =
                                getString(R.string.hru_resource, totalHru)
                            mruTextView.text =
                                getString(R.string.mru_resource, totalMru)
                            countryTextView.text =
                                getString(R.string.country, args.node.country)

                            val nodeLatitude = viewModel.getLatitude()
                            val nodeLongitude = viewModel.getLongitude()
                            buttonToMap.setOnClickListener {
                                val action = NodeDetailFragmentDirections.toMapFragment(
                                    args.node,
                                    nodeLatitude,
                                    nodeLongitude
                                )
                                findNavController().navigate(action)
                            }

                            progress.isVisible = false
                        }
                        is Lce.Error -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.error_message),
                                Toast.LENGTH_SHORT
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

    private fun calculatePercent(used: Double, total: Double): String {
        //пока как заглушка для ошибок
        if (used == 0.0 && total == 0.0) {
            return "0.00%"
        } else if (total == 0.0)
            return "0.00%"
        return try {
            val value = used / total * 100
            val result = String.format("%.2f", value)
            "$result%"
        } catch (e: RuntimeException) {
            "0.00%"
        }
    }
}