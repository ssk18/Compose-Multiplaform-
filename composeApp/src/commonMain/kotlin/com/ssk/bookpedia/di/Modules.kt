package com.ssk.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ssk.bookpedia.book.data.database.DatabaseFactory
import com.ssk.bookpedia.book.data.database.FavoriteBookDatabase
import com.ssk.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.ssk.bookpedia.book.data.network.RemoteBookDataSource
import com.ssk.bookpedia.book.data.repository.DefaultBookRepository
import com.ssk.bookpedia.book.domain.IBookRepository
import com.ssk.bookpedia.book.presentation.SelectedBookViewModel
import com.ssk.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.ssk.bookpedia.book.presentation.book_list.BookListViewmodel
import com.ssk.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultBookRepository).bind<IBookRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    viewModelOf(::BookListViewmodel)
    viewModelOf(::SelectedBookViewModel)
    viewModelOf(::BookDetailViewModel)
}