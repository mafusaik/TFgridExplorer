package by.homework.hlazarseni.tfgridexplorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.homework.hlazarseni.tfgridexplorer.databinding.NodeDetailFragmentBinding

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

        with(binding){
            toolbarDetail.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            val upTime = TimeConverter().timeToString(args.uptime.toLong())
            textView.text = upTime
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}