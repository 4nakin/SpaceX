package team.lf.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentAllLaunchesBinding
import timber.log.Timber

class LaunchesFragment : Fragment() {

    private val viewModel: LaunchesViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(this, LaunchesViewModel.Factory(activity.application))
            .get(LaunchesViewModel::class.java)
    }

    private lateinit var launchesAdapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAllLaunchesBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_all_launches, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        launchesAdapter = LaunchesAdapter(OnLaunchClickListener {
            Toast.makeText(this.activity, it.flight_number, Toast.LENGTH_SHORT).show()
            Timber.d(it.links.mission_patch_small)
            findNavController().navigate(LaunchesFragmentDirections.actionLaunchesFragmentToLaunchDetailFragment(it))
        })
        binding.root.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = launchesAdapter
        }
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {
            if (it) onNetworkError()
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.allLaunches.observe(viewLifecycleOwner, Observer {
            launchesAdapter.submitLaunchListAsync(it)
        })
        viewModel.refreshAllLaunchesFromRepository()
    }

    private fun onNetworkError() {
        if (!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}