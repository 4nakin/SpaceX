package team.lf.spacex.ui.launch_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentLaunchDetailsPagePhotosBinding
import team.lf.spacex.databinding.FragmentLaunchDetailsPageTextBinding
import team.lf.spacex.domain.Launch
import timber.log.Timber
import java.lang.IllegalStateException

class PlaceHolderFragment : Fragment() {

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, launch: Launch): PlaceHolderFragment {
            return PlaceHolderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putParcelable("launch", launch)
                }
            }
        }
    }

    private val viewModel: LaunchDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val args = arguments!!
        val launch: Launch = args.getParcelable("launch")!!
        ViewModelProvider(this, LaunchDetailViewModel.Factory(activity.application, launch))
            .get(LaunchDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        when (arguments!!.getInt("section_number")) {
            1 -> {
                val binding: FragmentLaunchDetailsPageTextBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_launch_details_page_text, container, false
                )
                binding.viewModel = viewModel
                binding.lifecycleOwner = viewLifecycleOwner
                return binding.root
            }
            2 -> {
                val binding: FragmentLaunchDetailsPagePhotosBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_launch_details_page_photos, container, false
                )
                binding.viewModel = viewModel
                binding.lifecycleOwner = viewLifecycleOwner
                return binding.root
            }
            else -> throw IllegalStateException()
        }

    }

}