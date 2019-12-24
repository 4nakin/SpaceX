package team.lf.spacex.ui.launches

import androidx.lifecycle.LiveData
import team.lf.spacex.data.repository.SpaceXRepository
import team.lf.spacex.data.ui_models.Launch
import javax.inject.Inject

class LaunchesInteract @Inject constructor(
    val repository: SpaceXRepository
) {
    fun getAllLaunchesFromDatabase(): LiveData<List<Launch>> = repository.allLaunches

    suspend fun loadLaunches(currentFilterType: LaunchesFilterType) {
        when (currentFilterType) {
            LaunchesFilterType.ALL_LAUNCHES -> repository.loadAllLaunches()
            LaunchesFilterType.PAST_LAUNCHES -> repository.loadPastLaunches()
            LaunchesFilterType.UPCOMMING_LAUNCHES -> repository.loadUpcomingLaunches()
            LaunchesFilterType.LATEST_LAUNCH -> repository.loadLatestLaunch()
            LaunchesFilterType.NEXT_LAUNCH -> repository.loadNextLaunch()
        }
    }
}