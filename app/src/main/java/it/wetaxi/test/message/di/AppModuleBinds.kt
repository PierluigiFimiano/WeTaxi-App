package it.wetaxi.test.message.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import it.wetaxi.test.message.data.DefaultMessageRepository
import it.wetaxi.test.message.data.MessageRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModuleBinds {

    @Binds
    @Singleton
    abstract fun bindMessageRepository(repository: DefaultMessageRepository): MessageRepository
}