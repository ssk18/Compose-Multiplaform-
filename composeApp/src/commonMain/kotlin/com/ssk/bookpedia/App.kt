package com.ssk.bookpedia

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.ssk.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.ssk.bookpedia.book.data.repository.DefaultBookRepository
import com.ssk.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.ssk.bookpedia.book.presentation.book_list.BookListViewmodel
import com.ssk.bookpedia.core.data.HttpClientFactory
import io.ktor.client.engine.HttpClientEngine
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(engine: HttpClientEngine) {
    BookListScreenRoot(
        viewmodel = remember {
            BookListViewmodel(
                bookRepository = DefaultBookRepository(
                    remoteBookDataSource = KtorRemoteBookDataSource(
                        httpClient = HttpClientFactory.create(
                            engine = engine
                        )
                    )
                )
            )
        },
        onBookClick = {

        }
    )
}