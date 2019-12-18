package team.lf.spacex.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import team.lf.spacex.data.database.SpaceXDatabase
import team.lf.spacex.data.network.SpaceXApiService
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDataBase ( context: Context): SpaceXDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            SpaceXDatabase::class.java,
            "launches.db"
        ).build()
    }

    @Singleton
    @Provides
    fun providesMoshi(): Moshi =  Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    
    @Singleton
    @Provides
    fun providesRetrofit(moshi: Moshi) = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(SpaceXApiService.BASE_URL)
        .build()
    
    @Singleton
    @Provides
    fun providesService(retrofit: Retrofit) = retrofit.create(SpaceXApiService::class.java)
    
}