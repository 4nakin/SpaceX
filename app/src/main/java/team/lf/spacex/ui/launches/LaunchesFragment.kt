package team.lf.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentAllLaunchesBinding

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
            viewModel.onLaunchDetailNavigate(it)
        })
        binding.root.findViewById<RecyclerView>(R.id.recycler).apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = launchesAdapter
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.allLaunches.observe(viewLifecycleOwner, Observer {
            launchesAdapter.submitLaunchListAsync(it)
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.all_launches_fragment_title)
        })
        viewModel.isLaunchDetailNavigate.observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.launchToWatch.value?.let{launch->
                    findNavController().navigate(LaunchesFragmentDirections.actionLaunchesFragmentToViewPagerFragment(launch))
                    viewModel.onLaunchDetailNavigated()
                }

            }
        })
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer {
            if (it) onNetworkError()
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