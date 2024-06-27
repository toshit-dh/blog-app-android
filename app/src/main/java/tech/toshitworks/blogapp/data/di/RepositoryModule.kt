package tech.toshitworks.blogapp.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import tech.toshitworks.blogapp.data.datastore.DataStoreManager
import tech.toshitworks.blogapp.data.datastore.PreferenceManager
import tech.toshitworks.blogapp.data.repository.BlogAppRepositoryImpl
import tech.toshitworks.blogapp.domain.BlogAppRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bingBlogAppRepository(
        blogAppRepositoryImpl: BlogAppRepositoryImpl
    ): BlogAppRepository

}