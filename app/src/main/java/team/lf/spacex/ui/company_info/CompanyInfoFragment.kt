package team.lf.spacex.ui.company_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerFragment
import team.lf.spacex.data.EventObserver
import team.lf.spacex.databinding.FragmentCompanyInfoBinding
import team.lf.spacex.setTittle
import timber.log.Timber
import javax.inject.Inject

/**
 * Main UI for the CompanyInfo screen.
 *
 */

class CompanyInfoFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentCompanyInfoBinding


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CompanyInfoViewModel> { viewModelFactory }

    private lateinit var eventsAdapter: EventsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = FragmentCompanyInfoBinding.inflate(inflater, container, false)
            .apply {
                viewmodel = viewModel
            }
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewDataBinding.lifecycleOwner = this.viewLifecycleOwner
        setupEventsAdapter()
        setupNetworkErrorEvent()
        viewModel.companyInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                setTittle(it.name)
            }
        })
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

    private fun setupEventsAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            eventsAdapter = EventsAdapter()
            viewDataBinding.historyList.adapter = eventsAdapter
        } else {
            Timber.w("ViewModel not initialized when attempting to set up adapter.")
        }

    }
}
