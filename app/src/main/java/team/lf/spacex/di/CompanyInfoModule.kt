package team.lf.spacex.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import team.lf.spacex.ui.company_info.CompanyInfoFragment
import team.lf.spacex.ui.company_info.CompanyInfoViewModel
import team.lf.spacex.ui.launches.LaunchesFragment
import team.lf.spacex.ui.launches.LaunchesViewModel


/**
 * Dagger module for the launches list feature.
 */
@Module
abstract class CompanyInfoModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun companyInfoFragment(): CompanyInfoFragment

    @Binds
    @IntoMap
    @ViewModelKey(CompanyInfoViewModel::class)
    abstract fun bindViewModel(viewmodel: CompanyInfoViewModel): ViewModel
}