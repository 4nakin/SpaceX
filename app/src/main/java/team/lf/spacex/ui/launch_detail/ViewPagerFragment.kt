package team.lf.spacex.ui.launch_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import team.lf.spacex.R
import team.lf.spacex.databinding.FragmentViewpagerBinding

class ViewPagerFragment : Fragment() {

    private val args: ViewPagerFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentViewpagerBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_viewpager, container, false
        )
        val sectionsPagerAdapter = SectionsPagerAdapter(this, args.launch)
        binding.pager.apply {
            adapter = sectionsPagerAdapter
        }

        TabLayoutMediator(binding.tabs, binding.pager){
            tab, position ->
            run {
                tab.text = context?.resources?.getText(SectionsPagerAdapter.TAB_TITLES[position])

            }
        }.attach()

        return binding.root
    }


}