package team.lf.spacex.utils

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import team.lf.spacex.R
import team.lf.spacex.ScrollChildSwipeRefreshLayout

fun Fragment.setupRefreshLayout(
    refreshLayout: ScrollChildSwipeRefreshLayout,
    scrollUpChild: View? = null
) {
    refreshLayout.setColorSchemeColors(
        ContextCompat.getColor(requireActivity(),
            R.color.colorPrimary
        ),
        ContextCompat.getColor(requireActivity(),
            R.color.colorAccent
        ),
        ContextCompat.getColor(requireActivity(),
            R.color.colorPrimaryDark
        )
    )
    // Set the scrolling view in the custom SwipeRefreshLayout.
    scrollUpChild?.let {
        refreshLayout.scrollUpChild = it
    }
}

fun Fragment.setTittle(title: String?) {
    title?.let {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}

fun Fragment.setTittle(tittle: Int?) {
    tittle?.let {
        (activity as AppCompatActivity).supportActionBar?.title = context?.resources?.getText(tittle)
    }
}

