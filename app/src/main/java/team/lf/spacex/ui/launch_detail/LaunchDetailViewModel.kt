package team.lf.spacex.ui.launch_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import team.lf.spacex.data.Event
import team.lf.spacex.data.database.getDatabase
import team.lf.spacex.data.domain.Launch
import team.lf.spacex.data.repository.SpaceXRepository


class LaunchDetailViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository = SpaceXRepository(
        getDatabase(
            application.applicationContext
        )
    )

    private val _launchFlightNumber = MutableLiveData<String>()
    private val _launch = switchMap(_launchFlightNumber){
        repository.getLaunchByFlightNumberFromDatabase(_launchFlightNumber.value!!)
    }
    val launch: LiveData<Launch?> = _launch

    val isDataAvailable: LiveData<Boolean> = map(_launch){ it != null }

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _redditButtonClickedEvent = MutableLiveData<Event<Unit>>()
    val redditButtonClicked: LiveData<Event<Unit>> = _redditButtonClickedEvent

    private val _wikiButtonClickedEvent = MutableLiveData<Event<Unit>>()
    val wikiButtonClickedEvent: LiveData<Event<Unit>> = _wikiButtonClickedEvent

    private val _youtubeButtonClickedEvent = MutableLiveData<Event<Unit>>()
    val youtubeButtonClicked: LiveData<Event<Unit>> = _youtubeButtonClickedEvent

    private var _isSMButtonClicked = MutableLiveData<Boolean>()
    val isSMButtonClicked: LiveData<Boolean>
        get() = _isSMButtonClicked
    private var _path = MutableLiveData<String>()
    val path: LiveData<String>
        get() = _path

    fun onRedditButtonClicked() {
        this.launch.value?.let {
            _path.value = it.links.reddit_campaign
            _redditButtonClickedEvent.value = Event(Unit)
        }
    }

    fun onYoutubeButtonClicked() {
        this.launch.value?.let {
            _path.value = it.links.video_link
            _youtubeButtonClickedEvent.value =Event(Unit)
        }
    }

    fun onWikiButtonClicked() {
        this.launch.value?.let {
            _path.value = it.links.wikipedia
            _wikiButtonClickedEvent.value = Event(Unit)
        }
    }

    fun start(launchFlightNumber: String?) {
        // If we're already loading or already loaded, return (might be a config change)
        if (_dataLoading.value == true || launchFlightNumber == _launchFlightNumber.value) {
            return
        }
        // Trigger the load
        _launchFlightNumber.value = launchFlightNumber
    }



    private var _isScrollerAlphaAnimation = MutableLiveData<Boolean>().apply {
        value = true
    }
    val isScrollerAlphaAnimation: LiveData<Boolean>
        get() = _isScrollerAlphaAnimation

    fun onScrollerAlphaAnimated(){
        _isScrollerAlphaAnimation.value = false
    }
}