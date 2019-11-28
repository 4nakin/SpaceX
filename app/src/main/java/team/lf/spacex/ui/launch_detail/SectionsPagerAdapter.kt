package team.lf.spacex.ui.launch_detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import team.lf.spacex.R
import team.lf.spacex.domain.Launch





/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(fragment: Fragment, private val launch: Launch) :
    FragmentStateAdapter(fragment) {

    companion object{
        val TAB_TITLES = arrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2)
    }

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        PlaceHolderFragment.newInstance(position + 1, launch)


}