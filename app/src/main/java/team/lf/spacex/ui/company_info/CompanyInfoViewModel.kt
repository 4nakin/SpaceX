package team.lf.spacex.ui.company_info

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import team.lf.spacex.domain.Launch
import team.lf.spacex.ui.launch_detail.LaunchDetailViewModel

class CompanyInfoViewModel(app: Application) : AndroidViewModel(app) {





    class Factory(private val app: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CompanyInfoViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CompanyInfoViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}
