package team.lf.spacex.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class Launch(
    val details: String,
    @PrimaryKey val flight_number: Int,
    val launch_date_local: String,
    val launch_date_unix: Int,
    val launch_date_utc: String,
    @Embedded val launch_site: LaunchSite,
    val launch_success: Boolean,
    val launch_year: String,
    @Embedded val links: Links,
    val mission_id: List<String>,
    val mission_name: String
)
data class Links(
    val article_link: String,
    val flickr_images: List<String>,
    val mission_patch: String,
    val mission_patch_small: String,
    val presskit: String,
    val reddit_campaign: String,
    val reddit_launch: String,
    val reddit_media: String,
    val video_link: String,
    val wikipedia: String,
    val youtube_id: String
)
data class LaunchSite(
    val site_id: String,
    val site_name: String,
    val site_name_long: String
)

fun List<Launch>.asDomainModels():List<team.lf.spacex.domain.Launch>{
    return map {
        team.lf.spacex.domain.Launch(
            it.details,
            it.flight_number,
            it.launch_date_utc,
            team.lf.spacex.domain.LaunchSite(
                it.launch_site.site_name,
                it.launch_site.site_name_long
            ),
            it.launch_success,
            team.lf.spacex.domain.Links(
                it.links.article_link,
                it.links.flickr_images,
                it.links.mission_patch,
                it.links.mission_patch_small,
                it.links.reddit_campaign,
                it.links.video_link,
                it.links.wikipedia
            ),
            it.mission_id,
            it.mission_name
        )
    }
}