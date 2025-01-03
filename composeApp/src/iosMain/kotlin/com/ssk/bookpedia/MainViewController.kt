package com.ssk.bookpedia

import androidx.compose.ui.window.ComposeUIViewController
import com.ssk.bookpedia.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }