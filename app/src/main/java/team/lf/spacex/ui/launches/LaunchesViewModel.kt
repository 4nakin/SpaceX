package team.lf.spacex.ui.launches

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import team.lf.spacex.Event
import team.lf.spacex.database.getDatabase
import team.lf.spacex.domain.Launch
import team.lf.spacex.repository.SpaceXRepository
import timber.log.Timber
import java.io.IOException

class LaunchesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceXRepository(getDatabase(application.applicationContext))

    private val _allLaunches: LiveData<List<Launch>> = repository.allLaunches
    val allLaunches = _allLaunches

    private val _openLaunchEvent = MutableLiveData<Event<Launch>>()
    val openLaunchEvent: LiveData<Event<Launch>> = _openLaunchEvent

    private val _networkErrorEvent = MutableLiveData<Event<Boolean>>()
    val networkErrorEvent: LiveData<Event<Boolean>> = _networkErrorEvent

    init {
        refreshAllLaunchesFromRepository()
    }

    fun refreshAllLaunchesFromRepository() {
        viewModelScope.launch {
            try {
                repository.refreshAllLaunches()
                _networkErrorEvent.value = Event(false)
            } catch (networkError: IOException) {
                _networkErrorEvent.value = Event(true)

            }
        }
    }

    fun openLaunch(launch: Launch) {
        _openLaunchEvent.value = Event(launch)
    }
}