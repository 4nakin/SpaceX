package team.lf.spacex.ui.launches

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import team.lf.spacex.domain.Launch

@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, items: List<Launch>?) {
    items?.let {
        (listView.adapter as LaunchesAdapter).submitLaunchListAsync(items)
    }
}