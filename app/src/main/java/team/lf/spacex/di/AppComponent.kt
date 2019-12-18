package team.lf.spacex.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import team.lf.spacex.SpaceXApp
import javax.inject.Singleton


/**
 * Main component for the application.
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        LaunchesModule::class,
        LaunchesDetailModule::class
    ]
)
interface AppComponent : AndroidInjector<SpaceXApp> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}