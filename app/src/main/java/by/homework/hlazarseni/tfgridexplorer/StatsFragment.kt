package by.homework.hlazarseni.tfgridexplorer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.homework.hlazarseni.tfgridexplorer.databinding.StatsFragmentBinding
import retrofit2.*

class StatsFragment : Fragment() {
    private var _binding: StatsFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)


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

        GridProxyService.api
            .getStats()
            .apply {
                enqueue(object : Callback<Node> {
                    override fun onResponse(call: Call<Node>, response: Response<Node>) {
                        if (response.isSuccessful) {
                            val stats = response.body() ?: return
                            binding.nodesOnline.text = stats.nodes
                            // binding.totalCru.text = stats.totalCru

                        } else {
                            handleException(HttpException(response))
                        }
                    }

                    override fun onFailure(call: Call<Node>, t: Throwable) {
                        if (!call.isCanceled) {
                            handleException(t)
                        }
                        println()
                    }
                })
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleException(e: Throwable) {

    }
}