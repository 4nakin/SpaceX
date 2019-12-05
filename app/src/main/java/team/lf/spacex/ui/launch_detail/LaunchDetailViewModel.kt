package team.lf.spacex.ui.launch_detail

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import team.lf.spacex.data.database.getDatabase
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.data.repository.SpaceXRepository

class LaunchDetailViewModel(application: Application, launch: Launch) :
    AndroidViewModel(application) {

    private val repository = SpaceXRepository(
        getDatabase(
            application.applicationContext
        )
    )



    val launch = repository.getLaunchByFlightNumberFromDatabase(launch.flight_number)


    private var _isSMButtonClicked = MutableLiveData<Boolean>()
    val isSMButtonClicked: LiveData<Boolean>
        get() = _isSMButtonClicked
    private var _pathSM = MutableLiveData<String>()
    val pathSM: LiveData<String>
        get() = _pathSM

    fun onRedditButtonClicked() {
        this.launch.value?.let {
            _pathSM.value = it.links.reddit_campaign
            _isSMButtonClicked.value = true
        }
    }

    fun onYoutubeButtonClicked() {
        this.launch.value?.let {
            _pathSM.value = it.links.video_link
            _isSMButtonClicked.value = true
        }
    }

    fun onWikiButtonClicked() {
        this.launch.value?.let {
            _pathSM.value = it.links.wikipedia
            _isSMButtonClicked.value = true
        }
    }

    fun onSMNavigated() {
        _isSMButtonClicked.value = false
    }

    private var _isScrollerAlphaAnimation = MutableLiveData<Boolean>().apply {
        value = true
    }
    val isScrollerAlphaAnimation: LiveData<Boolean>
        get() = _isScrollerAlphaAnimation

    fun onScrollerAlphaAnimated(){
        _isScrollerAlphaAnimation.value = false
    }


    class Factory(private val app: Application, private val launch: Launch) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LaunchDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LaunchDetailViewModel(app, launch) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}