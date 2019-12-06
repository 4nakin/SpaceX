package team.lf.spacex.ui.launch_detail

import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import team.lf.spacex.data.EventObserver
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.databinding.FragmentLaunchDetailsPageBinding
import timber.log.Timber

private const val ARG_SECTION_NUMBER = "section_number"
const val ARG_LAUNCH_FLIGHT_NUMBER = "launch"


class LaunchDetailFragment : Fragment() {

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, launch: Launch): LaunchDetailFragment {
            return LaunchDetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                    putString(ARG_LAUNCH_FLIGHT_NUMBER, launch.flight_number)
                }
            }
        }
    }

    private val viewModel by viewModels<LaunchDetailViewModel>()

    private lateinit var viewBinding: FragmentLaunchDetailsPageBinding

    private lateinit var adapter: FlickrImagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.start(arguments!!.getString(ARG_LAUNCH_FLIGHT_NUMBER))
        Timber.d("fragment created")

        adapter = FlickrImagesAdapter()
        viewBinding = FragmentLaunchDetailsPageBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewmodel = viewModel
            recycler.adapter = adapter
        }
        when (arguments!!.getInt(ARG_SECTION_NUMBER)) {
            1 -> viewModel.setPhotoPage(false)
            2 -> viewModel.setPhotoPage(true)
            else -> throw IllegalStateException("wrong argument")
        }
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigateTo.observe(viewLifecycleOwner, EventObserver {
            if (!it.isBlank()) {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            } else {
                Toast.makeText(activity, "Empty path", Toast.LENGTH_LONG).show()
            }
        })
        viewModel.launch.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitListAsync(it)
            }
        })
        viewModel.isTextAlphaAnimation.observe(viewLifecycleOwner, EventObserver {
                viewBinding.scroller.alpha = 0f
                ObjectAnimator.ofFloat(viewBinding.scroller, View.ALPHA, 1f).apply {
                    startDelay = 1000
                    duration = 500
                }.start()
        })
    }

}