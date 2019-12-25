package team.lf.spacex.data.network

fun List<Launch>.asDatabaseModels(): List<team.lf.spacex.data.database.entity.Launch> {
    return map {
        team.lf.spacex.data.database.entity.Launch(
            details = it.details ?: "Empty",
            flight_number = it.flight_number.orEmpty(),
            launch_date_local = it.launch_date_local.orEmpty(),
            launch_date_unix = it.launch_date_unix.orEmpty(),
            launch_date_utc = it.launch_date_utc.orEmpty(),
            launch_success = it.launch_success ?: false,
            launch_year = it.launch_year.orEmpty(),
            mission_id = it.mission_id?.joinToString().orEmpty(),
            mission_name = it.mission_name.orEmpty(),
            launch_site = team.lf.spacex.data.database.entity.LaunchSite(
                it.launch_site?.site_id.orEmpty(),
                it.launch_site?.site_name.orEmpty(),
                it.launch_site?.site_name_long.orEmpty()
            ),
            links = team.lf.spacex.data.database.entity.Links(
                article_link = it.links.article_link.orEmpty(),
                flickr_images = it.links.flickr_images?.joinToString().orEmpty(),
                mission_patch = it.links.mission_patch.orEmpty(),
                mission_patch_small = it.links.mission_patch_small.orEmpty(),
                presskit = it.links.presskit.orEmpty(),
                reddit_campaign = it.links.reddit_campaign.orEmpty(),
                reddit_launch = it.links.reddit_launch.orEmpty(),
                reddit_media = it.links.reddit_media.orEmpty(),
                video_link = it.links.video_link.orEmpty(),
                wikipedia = it.links.wikipedia.orEmpty(),
                youtube_id = it.links.youtube_id.orEmpty()
            ),
            upcoming = it.upcoming ?: false
        )
    }
}

