package com.ssk.bookpedia.book.data.repository

import com.ssk.bookpedia.book.data.mappers.toBook
import com.ssk.bookpedia.book.data.network.RemoteBookDataSource
import com.ssk.bookpedia.book.domain.Book
import com.ssk.bookpedia.book.domain.IBookRepository
import com.ssk.bookpedia.core.domain.DataError
import com.ssk.bookpedia.core.domain.Result
import com.ssk.bookpedia.core.domain.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
) : IBookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto ->
            dto.results.map {
                it.toBook()
            }
        }
    }
}