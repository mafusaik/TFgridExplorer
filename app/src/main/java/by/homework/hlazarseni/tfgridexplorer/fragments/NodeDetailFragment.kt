package by.homework.hlazarseni.tfgridexplorer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.homework.hlazarseni.tfgridexplorer.R
import by.homework.hlazarseni.tfgridexplorer.util.TimeConverter
import by.homework.hlazarseni.tfgridexplorer.databinding.NodeDetailFragmentBinding
import coil.load


class NodeDetailFragment : Fragment() {
    private var _binding: NodeDetailFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val args by navArgs<NodeDetailFragmentArgs>()


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
            toolbarDetail.setNavigationOnClickListener {
                findNavController().navigateUp()

            }

            detailImage.load(R.drawable.ic_fordetailnode)

            val totalCru = args.totalResourcesCru.toInt()
            val usedCru = args.usedResourcesCru.toInt()
            val totalSru = (args.totalResourcesSru / 1000000000).toDouble()
            val usedSru = (args.usedResourcesSru / 1000000000).toDouble()
            val totalHru = (args.totalResourcesHru / 1000000000).toDouble()
            val usedHru = (args.usedResourcesHru / 1000000000).toDouble()
            val totalMru = (args.totalResourcesMru / 1000000).toDouble()
            val usedMru = (args.usedResourcesMru / 1000000).toDouble()

            progressCru.max = totalCru
            progressCru.progress = usedCru
            progressSru.max = totalSru.toInt()
            progressSru.progress = usedSru.toInt()
            progressHru.max = totalHru.toInt()
            progressHru.progress = usedHru.toInt()
            progressMru.max = totalMru.toInt()
            progressMru.progress = usedMru.toInt()

            percentCruText.text = calculatePercent(usedCru.toDouble(), totalCru.toDouble())
            percentSruText.text = calculatePercent(usedSru, totalSru)
            percentHruText.text = calculatePercent(usedHru, totalHru)
            percentMruText.text = calculatePercent(usedMru, totalMru)

            val upTime = TimeConverter().timeToString(args.uptime.toLong())
            uptimeTextView.text = upTime

            idTextView.text = String.format("ID: %4s", args.nodeId)
            farmIdTextView.text = String.format("Farm ID: %4s", args.nodeId)
            cruTextView.text = "CPU Resource Unit: $totalCru GB"
            sruTextView.text = "Disk Resource Unit (SSD): $totalSru GB"
            hruTextView.text = "Disk Resource Unit (HDD): $totalHru GB"
            mruTextView.text = "Memory Resource Unit: $totalMru GB"


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun calculatePercent(used: Double, total: Double): String {
        val value = used / total * 100
        val result = String.format("%.2f", value)
        return "$result%"
    }
}