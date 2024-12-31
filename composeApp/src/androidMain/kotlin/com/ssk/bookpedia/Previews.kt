package com.ssk.bookpedia

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ssk.bookpedia.book.domain.Book
import com.ssk.bookpedia.book.presentation.book_list.BookListScreen
import com.ssk.bookpedia.book.presentation.book_list.BookListState
import com.ssk.bookpedia.book.presentation.book_list.components.BookListItem


private val books  = (1..100).map {
    Book(
        id = it.toString(),
        title = "Advanced Kotlin",
        authors = listOf("Marcin Moksala"),
        averageRating = 4.0,
        imageUrl = "",
        description = "Programming Book",
        languages = emptyList(),
        numPages = 300,
        numEditions = 4,
        ratingCount = 400,
        firstPublishYear = "2018"
    )
}

@Preview(showBackground = true)
@Composable
private fun BookSearchBarPreview() {
    MaterialTheme {
        BookListScreen(
           state = BookListState(
               searchQuery = "Kotlin",
               searchResults = books,
               favoriteBooks = emptyList(),
               isLoading = false,
               selectedTabIndex = 1,
               errorMessage = null
           ),
            onAction = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun BookListItemPreview() {
    BookListItem(
        book = Book(
            id = "1",
            title = "Advanced Kotlin",
            authors = listOf("Marcin Moksala"),
            averageRating = 4.0,
            imageUrl = "",
            description = "Programming Book",
            languages = emptyList(),
            numPages = 300,
            numEditions = 4,
            ratingCount = 400,
            firstPublishYear = "2018"
        ),
        onClick = {}
    )
}