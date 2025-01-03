package com.ssk.bookpedia.book.data.network

import com.ssk.bookpedia.book.data.dto.BookWorkDto
import com.ssk.bookpedia.book.data.dto.SearchResponseDto
import com.ssk.bookpedia.core.domain.DataError
import com.ssk.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(
        bookWorkId: String
    ): Result<BookWorkDto, DataError.Remote>
}