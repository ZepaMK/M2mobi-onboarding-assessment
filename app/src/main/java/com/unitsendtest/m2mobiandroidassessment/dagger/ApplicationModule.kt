package com.unitsendtest.m2mobiandroidassessment.dagger

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.unitsendtest.data.image.RemoteImageRepository
import com.unitsendtest.data.image.network.ImageApiService
import com.unitsendtest.data.image.storage.ImageDataStore
import com.unitsendtest.data.image.storage.MemoryImageDataStore
import com.unitsendtest.domain.data.ImageRepository
import com.unitsendtest.m2mobiandroidassessment.BuildConfig
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ApplicationModule {

    @Binds
    fun bindImageRepository(repository: RemoteImageRepository): ImageRepository

    @Binds
    @Singleton
    fun bindImagesDataStore(imageDataStore: MemoryImageDataStore): ImageDataStore

    companion object {

        @Provides
        @Reusable
        fun provideGson() = GsonBuilder().create()

        @Provides
        @Reusable
        fun provideRetrofit(gson: Gson): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

        @Provides
        @Singleton
        fun provideImageServiceApi(retrofit: Retrofit): ImageApiService =
            retrofit.create(ImageApiService::class.java)
    }
}
