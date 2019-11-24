package team.lf.spacex.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Launch(
    val details: String,
    val flight_number: Int,
    val launch_date_utc: String,
    val launch_site: LaunchSite,
    val launch_success: Boolean,
    val links: Links,
    val mission_id: List<String>,
    val mission_name: String
):Parcelable
@Parcelize
data class Links(
    val article_link: String,
    val flickr_images: List<String>,
    val mission_patch: String,
    val mission_patch_small: String,
    val reddit_campaign: String,
    val video_link: String,
    val wikipedia: String
):Parcelable

@Parcelize
data class LaunchSite(
    val site_name: String,
    val site_name_long: String
):Parcelable
