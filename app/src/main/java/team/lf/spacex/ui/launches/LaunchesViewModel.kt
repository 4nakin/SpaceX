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


    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun refreshAllLaunchesFromRepository() {
        viewModelScope.launch {
            try {
                Timber.d("refreshLaunches")
                repository.refreshAllLaunches()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false
            } catch (networkError: IOException) {
                Timber.d(networkError)
                _eventNetworkError.value = true
            }
        }
    }

    private val _openLaunchEvent = MutableLiveData<Event<Launch>>()
    val openLaunchEvent: LiveData<Event<Launch>> = _openLaunchEvent

    fun openLaunch(launch: Launch){
        _openLaunchEvent.value = Event(launch)
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }
}