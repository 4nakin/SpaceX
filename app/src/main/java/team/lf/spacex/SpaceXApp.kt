package team.lf.spacex

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import team.lf.spacex.di.DaggerAppComponent
import timber.log.Timber

class SpaceXApp : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.factory().create(applicationContext)

}