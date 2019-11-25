package team.lf.spacex

import android.app.Application
import timber.log.Timber

class SpaceXApp:Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}