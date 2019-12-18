package team.lf.spacex.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.lf.spacex.data.Event
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.data.repository.SpaceXRepository
import java.io.IOException
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(private val repository: SpaceXRepository) : ViewModel() {


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

        viewModelScope.launch {
            _dataLoading.value = true
            try {
                repository.refreshAllLaunches()
                _networkErrorEvent.value = Event(false)
                _dataLoading.value = false

            } catch (networkError: IOException) {
                _networkErrorEvent.value = Event(true)
            }
            _dataLoading.value = false
        }
    }

    fun openLaunch(launch: Launch) {
        _openLaunchEvent.value = Event(launch)
    }
}