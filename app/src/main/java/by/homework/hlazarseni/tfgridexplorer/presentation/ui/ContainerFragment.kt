package by.homework.hlazarseni.tfgridexplorer.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import by.homework.hlazarseni.tfgridexplorer.R
import by.homework.hlazarseni.tfgridexplorer.databinding.ContainerFragmentBinding

class ContainerFragment : Fragment() {

    private var _binding: ContainerFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ContainerFragmentBinding.inflate(inflater, container, false)
            .also { binding ->
                _binding = binding
            }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if(savedInstanceState == null){
            super.onViewCreated(view, savedInstanceState)
        }


        with(binding) {
            val nestedController =
                (childFragmentManager
                    .findFragmentById(R.id.page_container) as NavHostFragment)
                    .navController

            fragmentsNavigation.setupWithNavController(nestedController)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}