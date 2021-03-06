package team.lf.spacex.ui.launch_detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import team.lf.spacex.data.Event
import team.lf.spacex.data.ui_models.Launch
import team.lf.spacex.data.repository.SpaceXRepository
import javax.inject.Inject


class LaunchDetailViewModel @Inject constructor(private val repository: SpaceXRepository) :
    ViewModel() {

    private val _launchFlightNumber = MutableLiveData<String>()
    private val _launch = switchMap(_launchFlightNumber) {
        repository.getLaunchByFlightNumberFromDatabase(_launchFlightNumber.value!!)
    }
    val launch: LiveData<Launch?> = _launch

    private val _isPhotoPage = MutableLiveData<Boolean>()
    val isPhotoPage: LiveData<Boolean> = _isPhotoPage

    private val _isPhotosAvailable = MutableLiveData<Boolean>(true)
    val isPhotosAvailable: LiveData<Boolean> = _isPhotosAvailable


    private var _isTextAlphaAnimation = MutableLiveData<Event<Unit>>()
    val isTextAlphaAnimation: LiveData<Event<Unit>> = _isTextAlphaAnimation

    private var _navigateTo = MutableLiveData<Event<String>>()
    val navigateTo: LiveData<Event<String>> = _navigateTo

    fun start(launchFlightNumber: String?) {
        // If we're already loading or already loaded, return (might be a config change)
        if (launchFlightNumber == _launchFlightNumber.value) {
            return
        }
        // Trigger the load
        _launchFlightNumber.value = launchFlightNumber
        _isTextAlphaAnimation.value = Event(Unit)

    }

    fun setPhotoPage(set: Boolean) {
        _isPhotoPage.value = set
    }

    fun onRedditButtonClicked() {
        this.launch.value?.let {
            _navigateTo.value = Event(it.links.reddit_campaign)
        }
    }

    fun onYoutubeButtonClicked() {
        this.launch.value?.let {
            _navigateTo.value = Event(it.links.video_link)
        }
    }

    fun onWikiButtonClicked() {
        this.launch.value?.let {
            _navigateTo.value = Event(it.links.wikipedia)
        }
    }

    fun setPhotosUnAvailable(){
        _isPhotosAvailable.value = false
    }
}