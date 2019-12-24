package team.lf.spacex.data.database

import team.lf.spacex.data.database.entity.Launch

/**
 * Extensions for DBModels
 */

fun Launch.asDomainLaunchModel(): team.lf.spacex.data.ui_models.Launch {
    return team.lf.spacex.data.ui_models.Launch(
        details,
        flight_number,
        launch_date_unix,
        team.lf.spacex.data.ui_models.Links(
            links.flickr_images.split(","),
            links.mission_patch_small,
            links.reddit_campaign,
            links.video_link,
            links.wikipedia
        ),
        mission_name
    )
}

fun List<Launch>.asDomainModels(): List<team.lf.spacex.data.ui_models.Launch> {
    return map {
        it.asDomainLaunchModel()
    }
}