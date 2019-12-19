package team.lf.spacex.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import team.lf.spacex.ui.launch_detail.LaunchDetailFragment
import team.lf.spacex.ui.launch_detail.LaunchDetailViewModel

/**
 * Dagger module for the launch details.
 */
@Module
abstract class LaunchesDetailModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun launchesDetailFragment(): LaunchDetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(LaunchDetailViewModel::class)
    abstract fun bindViewModel(viewmodel: LaunchDetailViewModel): ViewModel
}