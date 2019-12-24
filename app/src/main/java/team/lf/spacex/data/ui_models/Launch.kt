package team.lf.spacex.data.ui_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Models for representing in ui
 */
@Parcelize
data class Launch(
    val details: String,
    val flight_number: String,
    val launch_date_unix: String,
    val links: Links,
    val mission_name: String
) : Parcelable

@Parcelize
data class Links(
    val flickr_images: List<String>,
    val mission_patch_small: String,
    val reddit_campaign: String,
    val video_link: String,
    val wikipedia: String
) : Parcelable


data class FlickrImage(val url: String)
