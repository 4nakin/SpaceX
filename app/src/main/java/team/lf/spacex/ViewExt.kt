package team.lf.spacex

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Fragment.setupRefreshLayout(
    refreshLayout: ScrollChildSwipeRefreshLayout,
    scrollUpChild: View? = null
) {
    refreshLayout.setColorSchemeColors(
        ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
        ContextCompat.getColor(requireActivity(), R.color.colorAccent),
        ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
    )
    // Set the scrolling view in the custom SwipeRefreshLayout.
    scrollUpChild?.let {
        refreshLayout.scrollUpChild = it
    }
}

//Mask photo
//https://upload.wikimedia.org/wikipedia/commons/e/ed/Elon_Musk_Royal_Society.jpg