package team.lf.spacex.ui.launch_detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import team.lf.spacex.database.getDatabase
import team.lf.spacex.domain.Launch
import team.lf.spacex.repository.SpaceXRepository

class LaunchDetailViewModel(application: Application, launch: Launch) :
    AndroidViewModel(application) {

    private val repository = SpaceXRepository(getDatabase(application.applicationContext))

    val launch = repository
    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

}