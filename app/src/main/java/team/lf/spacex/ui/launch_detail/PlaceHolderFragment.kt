package team.lf.spacex.ui.launch_detail

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentLaunchDetailsPagePhotosBinding
import team.lf.spacex.databinding.FragmentLaunchDetailsPageTextBinding
import team.lf.spacex.domain.Launch

private const val ARG_SECTION_NUMBER = "section_number"
const val ARG_LAUNCH = "launch"


class PlaceHolderFragment : Fragment() {

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, launch: Launch): PlaceHolderFragment {
            return PlaceHolderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putParcelable(ARG_LAUNCH, launch)
                }
            }
        }
    }

    private val viewModel: LaunchDetailViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        val launch: Launch = arguments!!.getParcelable(ARG_LAUNCH)!!
        ViewModelProvider(this, LaunchDetailViewModel.Factory(activity.application, launch))
            .get(LaunchDetailViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        when (arguments!!.getInt(ARG_SECTION_NUMBER)) {
            1 -> {
                val binding: FragmentLaunchDetailsPageTextBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_launch_details_page_text, container, false
                )
                binding.viewModel = viewModel
                binding.lifecycleOwner = viewLifecycleOwner

                //Жена сказала, что с такой анимацией красивее =)
                viewModel.isScrollerAlphaAnimation.observe(viewLifecycleOwner, Observer {
                    if(it){
                        binding.scroller.alpha = 0f
                        ObjectAnimator.ofFloat(binding.scroller, View.ALPHA, 1f).apply {
                            startDelay = 1500
                            duration = 500
                        }.start()
                        viewModel.onScrollerAlphaAnimated()
                    }
                })
                return binding.root
            }
            2 -> {
                val binding: FragmentLaunchDetailsPagePhotosBinding = DataBindingUtil.inflate(
                    inflater, R.layout.fragment_launch_details_page_photos, container, false
                )
                binding.viewModel = viewModel
                binding.lifecycleOwner = viewLifecycleOwner
                val adapter = FlickrImagesAdapter()
                binding.recycler.adapter = adapter
                viewModel.launch.observe(viewLifecycleOwner, Observer {
                    adapter.submitListAsync(it)
                })
                return binding.root
            }
            else -> throw IllegalStateException()
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.isSMButtonClicked.observe(viewLifecycleOwner, Observer {
            if (it) {
                val path = viewModel.pathSM.value
                if (!path.isNullOrBlank()) {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(path)))
                } else {
                    Toast.makeText(activity, "Empty path", Toast.LENGTH_LONG).show()
                }
                viewModel.onSMNavigated()
            }
        })
    }

}