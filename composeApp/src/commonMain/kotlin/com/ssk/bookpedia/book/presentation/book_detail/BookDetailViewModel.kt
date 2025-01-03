package com.ssk.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ssk.bookpedia.app.Route
import com.ssk.bookpedia.book.domain.IBookRepository
import com.ssk.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: IBookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val bookId = savedStateHandle.toRoute<Route.BookDetails>().id

    private val _bookDetail = MutableStateFlow(BookDetailState())
    val bookDetail = _bookDetail
        .onStart {
            fetchBookDescription()
            observeFavoriteStatus()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _bookDetail.value
        )

    fun onAction(action: BookDetailAction) {
        when (action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _bookDetail.update {
                    it.copy(
                        book = action.book
                    )
                }
            }

            is BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    if (_bookDetail.value.isFavorite) {
                        bookRepository.deleteFromFavorite(bookId)
                    } else {
                        _bookDetail.value.book?.let {
                            bookRepository.markAsFavorite(it)
                        }
                    }
                }
            }

            else -> Unit
        }
    }

    private fun observeFavoriteStatus() {
        bookRepository.isBookFavorite(bookId)
            .onEach { isfavorite ->
                _bookDetail.update {
                    it.copy(
                        isFavorite = isfavorite
                    )
                }
            }.launchIn(viewModelScope)
    }

    private fun fetchBookDescription() {
        viewModelScope.launch {
            bookRepository.getBookDescription(bookId)
                .onSuccess { description ->
                    _bookDetail.update {
                        it.copy(
                            isLoading = false,
                            book = it.book?.copy(
                                description = description
                            )
                        )
                    }
                }
        }
    }

}