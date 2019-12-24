package team.lf.spacex.ui.launches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import team.lf.spacex.R
import team.lf.spacex.data.Event
import team.lf.spacex.data.ui_models.Launch
import java.io.IOException
import javax.inject.Inject

class LaunchesViewModel @Inject constructor(private val launchesInteract: LaunchesInteract) :
    ViewModel() {

    private val _allLaunches: LiveData<List<Launch>> = launchesInteract.getAllLaunchesFromDatabase()
    val allLaunches = _allLaunches

    private val _openLaunchEvent = MutableLiveData<Event<Launch>>()
    val openLaunchEvent: LiveData<Event<Launch>> = _openLaunchEvent

    private val _networkErrorEvent = MutableLiveData<Event<Boolean>>()
    val networkErrorEvent: LiveData<Event<Boolean>> = _networkErrorEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private var _currentFilterType = LaunchesFilterType.PAST_LAUNCHES

    private val _currentFragmentTittle = MutableLiveData<Int>(R.string.past_launches_fragment_title)
    val currentFragmentTittle = _currentFragmentTittle

    init {
        refreshLaunches()
    }

    fun refreshLaunches() {
        viewModelScope.launch {
            _dataLoading.value = true
            try {
                launchesInteract.loadLaunches(_currentFilterType)
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

    fun setFilter(requestType: LaunchesFilterType) {
        _currentFilterType = requestType
        setTittle(requestType)
    }

    fun setTittle(requestType: LaunchesFilterType) {
        _currentFragmentTittle.value = when (requestType) {
            LaunchesFilterType.PAST_LAUNCHES -> R.string.past_launches_fragment_title
            LaunchesFilterType.UPCOMMING_LAUNCHES -> R.string.upcoming_launches_fragment_title
            LaunchesFilterType.LATEST_LAUNCH -> R.string.latest_launch_fragment_title
            LaunchesFilterType.NEXT_LAUNCH -> R.string.next_launch_fragment_title
            else -> R.string.all_launches_fragment_title
        }
    }
}




