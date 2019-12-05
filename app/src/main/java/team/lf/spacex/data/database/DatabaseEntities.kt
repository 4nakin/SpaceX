package team.lf.spacex.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "launches")
data class Launch(
    val details: String,
    @PrimaryKey val flight_number: String,
    val launch_date_local: String,
    val launch_date_unix: String,
    val launch_date_utc: String,
    @Embedded val launch_site: LaunchSite,
    val launch_success: Boolean,
    val launch_year: String,
    @Embedded val links: Links,
    val mission_id: String,
    val mission_name: String
)

data class Links(
    val article_link: String,
    val flickr_images: String,
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

fun List<Launch>.asDomainModels(): List<team.lf.spacex.data.domain.Launch> {
    return map {
        it.asDomainLaunchModel()
    }


}

fun Launch.asDomainLaunchModel(): team.lf.spacex.data.domain.Launch {
    return team.lf.spacex.data.domain.Launch(
        details,
        flight_number,
        launch_date_utc,
        launch_date_unix,
        team.lf.spacex.data.domain.LaunchSite(
            launch_site.site_name,
            launch_site.site_name_long
        ),
        launch_success,
        team.lf.spacex.data.domain.Links(
            links.article_link,
            links.flickr_images.split(","),
            links.mission_patch,
            links.mission_patch_small,
            links.reddit_campaign,
            links.video_link,
            links.wikipedia
        ),
        mission_id.split(","),
        mission_name

    )
}