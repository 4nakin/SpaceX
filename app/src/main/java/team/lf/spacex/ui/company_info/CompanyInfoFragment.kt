package team.lf.spacex.ui.company_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentAllLaunchesBinding
import team.lf.spacex.databinding.FragmentCompanyInfoBinding
import team.lf.spacex.setupRefreshLayout
import team.lf.spacex.ui.launches.LaunchesViewModel
import javax.inject.Inject

/**
 * Main UI for the CompanyInfo screen.
 *
 * isn't done!
 */

class CompanyInfoFragment : DaggerFragment() {

    private lateinit var viewDataBinding: FragmentCompanyInfoBinding


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<CompanyInfoViewModel> { viewModelFactory }

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
    }

}
