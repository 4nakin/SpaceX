package team.lf.spacex.ui.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import team.lf.spacex.data.EventObserver
import team.lf.spacex.databinding.FragmentAllLaunchesBinding
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.setupRefreshLayout
import timber.log.Timber

class LaunchesFragment : Fragment() {

    private lateinit var viewDataBinding: FragmentAllLaunchesBinding

    private val viewModel by viewModels<LaunchesViewModel>()

    private lateinit var listAdapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentAllLaunchesBinding.inflate(
            inflater, container, false
        ).apply {
            viewmodel = viewModel
        }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.recycler)
        setupListAdapter()
        setupNavigation()
        setupNetworkErrorEvent()
    }

    private fun setupNetworkErrorEvent() {
        viewModel.networkErrorEvent.observe(viewLifecycleOwner,
            EventObserver {
                if (it) {
                    Snackbar.make(viewDataBinding.root, "Network Error", Snackbar.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun setupNavigation() {
        viewModel.openLaunchEvent.observe(viewLifecycleOwner,
            EventObserver {
                openLaunch(it)
            })
    }

    private fun openLaunch(it: Launch) {
        findNavController().navigate(
            LaunchesFragmentDirections.actionLaunchesFragmentToViewPagerFragment(
                it
            )
        )
    }

    private fun setupListAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            listAdapter = LaunchesAdapter(viewModel)
            viewDataBinding.recycler.adapter = listAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }
    }

}