package com.ssk.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.ssk.bookpedia.book.data.database.FavoriteBookDao
import com.ssk.bookpedia.book.data.mappers.toBook
import com.ssk.bookpedia.book.data.mappers.toBookEntity
import com.ssk.bookpedia.book.data.network.RemoteBookDataSource
import com.ssk.bookpedia.book.domain.Book
import com.ssk.bookpedia.book.domain.IBookRepository
import com.ssk.bookpedia.core.domain.DataError
import com.ssk.bookpedia.core.domain.EmptyResult
import com.ssk.bookpedia.core.domain.Result
import com.ssk.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
) : IBookRepository {
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource.searchBooks(query).map { dto ->
            dto.results.map {
                it.toBook()
            }
        }
    }

    override suspend fun getBookDescription(bookWorkId: String): Result<String?, DataError> {
        val localResult = favoriteBookDao.getFavoriteBook(bookWorkId)?.toBook()
        return if (localResult == null) {
            remoteBookDataSource.getBookDetails(bookWorkId).map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao.getFavoriteBooks().map { bookEntities ->
            bookEntities.any {
                it.id == id
            }
        }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try {
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch (e: SQLiteException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorite(id: String) {
        favoriteBookDao.deleteFavoriteBook(id)
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao.getFavoriteBooks().map { books ->
            books.map {
                it.toBook()
            }
        }
    }
}