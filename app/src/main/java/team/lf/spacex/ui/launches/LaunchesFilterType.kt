package team.lf.spacex.ui.launches


/**
 * Used with the filter spinner in the launch list.
 */
enum class LaunchesFilterType {

    /**
     * Do not filter launches.
     */
    ALL_LAUNCHES,

    /**
     * Filters only the past (already launched) launches.
     */
    PAST_LAUNCHES,

    /**
     * Filters only the future (not yet launched) launches.
     */
    UPCOMMING_LAUNCHES,

    /**
     * Filters only the one last launch.
     */
    LATEST_LAUNCH,

    /**
     * Filters only next launch.
     */
    NEXT_LAUNCH

}