package team.lf.spacex.ui.launches

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import team.lf.spacex.data.Event
import team.lf.spacex.data.database.getDatabase
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.data.repository.SpaceXRepository
import java.io.IOException

class LaunchesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceXRepository(
        getDatabase(
            application.applicationContext
        )
    )

    private val _allLaunches: LiveData<List<Launch>> = repository.allLaunches
    val allLaunches = _allLaunches

    private val _openLaunchEvent = MutableLiveData<Event<Launch>>()
    val openLaunchEvent: LiveData<Event<Launch>> = _openLaunchEvent

    private val _networkErrorEvent = MutableLiveData<Event<Boolean>>()
    val networkErrorEvent: LiveData<Event<Boolean>> = _networkErrorEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        refreshAllLaunchesFromRepository()
    }

    fun refreshAllLaunchesFromRepository() {

        _dataLoading.value = true
        viewModelScope.launch {
            try {
                repository.refreshAllLaunches()
                _networkErrorEvent.value = Event(false)
                _dataLoading.value = false

            } catch (networkError: IOException) {
                _networkErrorEvent.value = Event(true)
                _dataLoading.value = false
            }
        }
    }

    fun openLaunch(launch: Launch) {
        _openLaunchEvent.value = Event(launch)
    }
}