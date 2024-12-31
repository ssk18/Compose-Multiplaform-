package com.ssk.bookpedia.book.domain

import com.ssk.bookpedia.core.domain.DataError
import com.ssk.bookpedia.core.domain.Result

interface IBookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
}