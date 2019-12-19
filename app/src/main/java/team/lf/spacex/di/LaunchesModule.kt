package team.lf.spacex.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import team.lf.spacex.ui.launches.LaunchesFragment
import team.lf.spacex.ui.launches.LaunchesViewModel


/**
 * Dagger module for the launches list feature.
 */
@Module
abstract class LaunchesModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun launchesFragment(): LaunchesFragment

    @Binds
    @IntoMap
    @ViewModelKey(LaunchesViewModel::class)
    abstract fun bindViewModel(viewmodel: LaunchesViewModel): ViewModel
}