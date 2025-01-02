package com.ssk.bookpedia.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookDetailViewModel: ViewModel() {

    private val _bookDetail = MutableStateFlow(BookDetailState())
    val bookDetail = _bookDetail.asStateFlow()

    fun onAction(action: BookDetailAction) {
        when(action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _bookDetail.update {
                    it.copy(
                        book = action.book
                    )
                }
            }
            else -> {

            }
        }
    }

}