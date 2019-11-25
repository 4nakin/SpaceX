package team.lf.spacex.ui.launches

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import team.lf.spacex.database.getDatabase
import team.lf.spacex.network.SpaceXApi
import team.lf.spacex.repository.SpaceXRepository
import timber.log.Timber
import java.io.IOException

class LaunchesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = SpaceXRepository(getDatabase(application.applicationContext))

    val allLaunches = repository.allLaunches

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private var _eventNetworkError = MutableLiveData<Boolean>(false)
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    fun refreshAllLAunchesFromRepository() {
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

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LaunchesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LaunchesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}