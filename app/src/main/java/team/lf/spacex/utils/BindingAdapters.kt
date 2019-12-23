package team.lf.spacex.utils

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import team.lf.spacex.R
import java.text.SimpleDateFormat
import java.util.*

@Suppress("unchecked_cast")
@BindingAdapter("app:items")
fun <T> setItems(listView: RecyclerView, items: List<T>?) {
    items?.let {
        (listView.adapter as ListAdapter<T, *>).submitList(it)
    }
}

@BindingAdapter("imageUrl")
fun ImageView.setImage(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .apply(
                RequestOptions()
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
            )
            .into(this)
    }
}

@BindingAdapter("flickrUrl")
fun ImageView.setFlickr(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(context)
            .load(imgUri)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .apply(
                RequestOptions()
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image)
                    )
            )
            .into(this)
    }
}

@BindingAdapter("shortDetails")
fun TextView.setShortDetails(detailsText: String?) {
    detailsText?.let {
        this.text = shortText(50, it)
    }
}

@BindingAdapter("shortMissionName")
fun TextView.setShortMissionName(missionName: String?) {
    missionName?.let {
        text = shortText(15, it)
    }
}

fun shortText(maxSize: Int, text: String): String {
    val resultText = StringBuilder()
    resultText.append(text.take(maxSize))
    if (text.length > maxSize) {
        resultText.append("...")
    }
    return resultText.toString()
}

@BindingAdapter("unixToDate")
fun TextView.setUnixDate(unix: String?) {
    unix?.let {
        text = it.toLong().dateToString()
    }
}

fun Long.dateToString(): String {
    val date = Date(this * 1000L)
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(date)
}




