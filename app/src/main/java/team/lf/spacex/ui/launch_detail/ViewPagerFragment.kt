package team.lf.spacex.ui.launch_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentViewpagerBinding

class ViewPagerFragment : Fragment() {

    private val args: ViewPagerFragmentArgs by navArgs()

    private lateinit var viewBinding: FragmentViewpagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_viewpager, container, false
        )

        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setPager()
        setTittle()
        setTabsText()
        setBadges()
    }

    private fun setPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, args.launch)
        viewBinding.pager.apply {
            adapter = sectionsPagerAdapter
        }
    }

    private fun setTittle() {
        (activity as AppCompatActivity).supportActionBar?.title = args.launch.mission_name
    }

    private fun setTabsText() {
        TabLayoutMediator(viewBinding.tabs, viewBinding.pager) { tab, position ->
            run {
                tab.text = context?.resources?.getText(SectionsPagerAdapter.TAB_TITLES[position])
            }
        }.attach()
    }

    //sets number of flikr photos to  photo tab badge
    private fun setBadges() {
        viewBinding.tabs.getTabAt(1)?.let {
            val badge = it.orCreateBadge
            badge.isVisible = true
            val images = args.launch.links.flickr_images
            badge.number = if (images[0] == "") {
                images.size - 1
            } else {
                images.size
            }
        }
    }


}