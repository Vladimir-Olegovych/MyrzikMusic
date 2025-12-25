package com.gigchad.music.core.di

import com.gigchad.music.core.service.ServiceBinder
import com.gigchad.music.service.MusicPlayerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideIOScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideMusicServiceBinder(): ServiceBinder<MusicPlayerService, MusicPlayerService.MusicPlayerBinder> {
        return ServiceBinder()
    }


}