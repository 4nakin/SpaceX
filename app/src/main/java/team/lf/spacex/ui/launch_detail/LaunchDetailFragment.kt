package team.lf.spacex.ui.launch_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentLaunchDetailBinding
import team.lf.spacex.domain.Launch
import team.lf.spacex.ui.launches.LaunchesViewModel

class LaunchDetailFragment : Fragment() {

    private val viewModel: LaunchDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args = LaunchDetailFragmentArgs.fromBundle(arguments!!)
        val launch: Launch = args.launch
        ViewModelProvider(this, LaunchDetailViewModel.Factory(activity.application, launch))
            .get(LaunchDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLaunchDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_launch_detail, container
            , false
        )
        binding.lifecycleOwner = viewLifecycleOwner

        binding.viewModel = viewModel


        return binding.root
    }
}