package team.lf.spacex.ui.launch_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentLaunchDetailBinding

class LaunchDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentLaunchDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_launch_detail, container
            , false
        )

        return binding.root
    }
}