package team.lf.spacex.ui.launches

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import team.lf.spacex.R
import team.lf.spacex.data.EventObserver
import team.lf.spacex.data.ui_models.Launch
import team.lf.spacex.databinding.FragmentAllLaunchesBinding
import team.lf.spacex.utils.setupRefreshLayout
import timber.log.Timber
import javax.inject.Inject

class LaunchesFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentAllLaunchesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LaunchesViewModel> { viewModelFactory }

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
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupRefreshLayout(viewDataBinding.refreshLayout, viewDataBinding.recycler)
        setTittleObserver()
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.launches_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_filter -> {
                showFilteringPopUpMenu()
                true
            }
            else -> false
        }

    private fun showFilteringPopUpMenu() {
        val view = activity?.findViewById<View>(R.id.menu_filter) ?: return
        PopupMenu(requireContext(), view).run {
            menuInflater.inflate(R.menu.filter_launches, menu)

            setOnMenuItemClickListener {
                viewModel.setFilter(
                    when (it.itemId) {
                        R.id.past_launches -> LaunchesFilterType.PAST_LAUNCHES
                        R.id.upcoming_launches -> LaunchesFilterType.UPCOMMING_LAUNCHES
                        R.id.latest_launch -> LaunchesFilterType.LATEST_LAUNCH
                        R.id.next_launch -> LaunchesFilterType.NEXT_LAUNCH
                        else -> LaunchesFilterType.ALL_LAUNCHES
                    }
                )
                viewModel.refreshLaunches()
                true
            }
            show()
        }
    }

    private fun setTittleObserver() {
        viewModel.currentFragmentTittle.observe(viewLifecycleOwner, Observer {
            it?.let {
                (activity as AppCompatActivity).supportActionBar?.title = context?.resources?.getText(it)
            }
        })
    }

}