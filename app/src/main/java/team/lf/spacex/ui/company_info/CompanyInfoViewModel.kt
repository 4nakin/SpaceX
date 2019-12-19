package team.lf.spacex.ui.company_info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import team.lf.spacex.data.Event
import team.lf.spacex.data.repository.SpaceXRepository
import team.lf.spacex.ui.company_info.data.CompanyInfo
import java.io.IOException
import javax.inject.Inject

/**
 * ViewModel for the CompanyInfo fragment.
 *
 * isn't done!
 */

class CompanyInfoViewModel @Inject constructor(private val repository: SpaceXRepository) :
    ViewModel() {

    private val _companyInfo = repository.companyInfo
    val companyInfo: LiveData<CompanyInfo> = _companyInfo

    private val _networkErrorEvent = MutableLiveData<Event<Boolean>>()
    val networkErrorEvent: LiveData<Event<Boolean>> = _networkErrorEvent

    private val _dataLoading = MutableLiveData<Boolean>()
    val dataLoading: LiveData<Boolean> = _dataLoading

    init {
        refreshCompanyInfo()
    }

    private fun refreshCompanyInfo() {
        viewModelScope.launch {
            _dataLoading.value = true
            try {
                repository.refreshCompanyInfo()
                _networkErrorEvent.value = Event(false)
                _dataLoading.value = false
            } catch (networkError: IOException) {
                _networkErrorEvent.value = Event(true)
            }
        }
    }


}
