package com.ssk.bookpedia.book.presentation.book_list

import com.ssk.bookpedia.book.domain.Book
import com.ssk.bookpedia.core.presentation.UiText

data class BookListState(
    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = true,
    var selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)
