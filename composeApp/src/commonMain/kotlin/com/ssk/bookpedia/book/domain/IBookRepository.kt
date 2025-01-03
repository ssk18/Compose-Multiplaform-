package com.ssk.bookpedia.book.domain

import com.ssk.bookpedia.core.domain.DataError
import com.ssk.bookpedia.core.domain.EmptyResult
import com.ssk.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError>
    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorite(id: String)
}