package team.lf.spacex.data.network

import team.lf.spacex.di.LaunchesModule


/**
 * Models from api
 */

data class NetworkModels(
    val launches: List<Launch>
)
data class Launch(
    val crew: List<String>?,
    val details: String?,
    val flight_number: String?,
    val is_tentative: Boolean?,
    val launch_date_local: String?,
    val launch_date_unix: String?,
    val launch_date_utc: String?,
    val launch_site: LaunchSite?,
    val launch_success: Boolean?,
    val launch_window: String?,
    val launch_year: String?,
    val links: Links,
    val mission_id: List<String>?,
    val mission_name: String?,
    val rocket: Rocket,
    val ships: List<String>?,
    val static_fire_date_unix: String?,
    val static_fire_date_utc: String?,
    val tbd: Boolean?,
    val telemetry: Telemetry?,
    val tentative_max_precision: String?,
    val timeline: Timeline?,
    val upcoming: Boolean?
)

data class LaunchSite(
    val site_id: String?,
    val site_name: String?,
    val site_name_long: String?
)

data class Links(
    val article_link: String?,
    val flickr_images: List<String>?,
    val mission_patch: String?,
    val mission_patch_small: String?,
    val presskit: String?,
    val reddit_campaign: String?,
    val reddit_launch: String?,
    val reddit_media: String?,
    val reddit_recovery: Any?,
    val video_link: String?,
    val wikipedia: String?,
    val youtube_id: String?
)

data class Rocket(
    val fairings: Any?,
    val first_stage: FirstStage?,
    val rocket_id: String?,
    val rocket_name: String?,
    val rocket_type: String?,
    val second_stage: SecondStage?
)

data class FirstStage(
    val cores: List<Core>?
)

data class Core(
    val block: String?,
    val core_serial: String?,
    val flight: String?,
    val gridfins: Boolean?,
    val land_success: Any?,
    val landing_intent: Boolean?,
    val landing_type: Any?,
    val landing_vehicle: Any?,
    val legs: Boolean?,
    val reused: Boolean?
)

data class SecondStage(
    val block: String?,
    val payloads: List<Payload>?
)

data class Payload(
    val cap_serial: String?,
    val cargo_manifest: String?,
    val customers: List<String>?,
    val flight_time_sec: String?,
    val manufacturer: String?,
    val mass_returned_kg: String?,
    val mass_returned_lbs: String?,
    val nationality: String?,
    val norad_id: List<String>?,
    val orbit: String?,
    val orbit_params: OrbitParams?,
    val payload_id: String?,
    val payload_mass_kg: String?,
    val payload_mass_lbs: String?,
    val payload_type: String?,
    val reused: Boolean?,
    val uid: String?
)

data class OrbitParams(
    val apoapsis_km: String?,
    val arg_of_pericenter: String?,
    val eccentricity: String?,
    val epoch: String?,
    val inclination_deg: String?,
    val lifespan_years: Any?,
    val longitude: Any?,
    val mean_anomaly: String?,
    val mean_motion: String?,
    val periapsis_km: String?,
    val period_min: String?,
    val raan: String?,
    val reference_system: String?,
    val regime: String?,
    val semi_major_axis_km: String?
)

data class Telemetry(
    val flight_club: String?
)

data class Timeline(
    val dragon_bay_door_deploy: String?,
    val dragon_separation: String?,
    val dragon_solar_deploy: String?,
    val engine_chill: String?,
    val go_for_launch: String?,
    val go_for_prop_loading: String?,
    val ignition: String?,
    val liftoff: String?,
    val maxq: String?,
    val meco: String?,
    val prelaunch_checks: String?,
    val propellant_pressurization: String?,
    val rp1_loading: String?,
    val seco_1: String?,
    val second_stage_ignition: String?,
    val stage1_lox_loading: String?,
    val stage2_lox_loading: String?,
    val stage_sep: String?,
    val webcast_liftoff: String?
)

fun List<Launch>.asDatabaseModels(): List<team.lf.spacex.data.database.Launch> {
    return map {
        team.lf.spacex.data.database.Launch(
            details = it.details ?: "Empty",
            flight_number = it.flight_number ?: "",
            launch_date_local = it.launch_date_local ?: "",
            launch_date_unix = it.launch_date_unix ?: "",
            launch_date_utc = it.launch_date_utc ?: "",
            launch_success = it.launch_success ?: false,
            launch_year = it.launch_year ?: "",
            mission_id = it.mission_id?.joinToString() ?: "",
            mission_name = it.mission_name ?: "",
            launch_site = team.lf.spacex.data.database.LaunchSite(
                it.launch_site?.site_id ?: "",
                it.launch_site?.site_name ?: "",
                it.launch_site?.site_name_long ?: ""
            ),
            links = team.lf.spacex.data.database.Links(
                article_link = it.links.article_link ?: "",
                flickr_images = it.links.flickr_images?.joinToString() ?: "",
                mission_patch = it.links.mission_patch ?: "",
                mission_patch_small = it.links.mission_patch_small ?: "",
                presskit = it.links.presskit ?: "",
                reddit_campaign = it.links.reddit_campaign ?: "",
                reddit_launch = it.links.reddit_launch ?: "",
                reddit_media = it.links.reddit_media ?: "",
                video_link = it.links.video_link ?: "",
                wikipedia = it.links.wikipedia ?: "",
                youtube_id = it.links.youtube_id ?: ""
            )
        )
    }
}