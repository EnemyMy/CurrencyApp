package com.example.app_41_currencyapp.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackbar(event: SnackbarEvent) {
    Snackbar.make(this, event.text, event.length).also {
        if (event.actionText != null && event.action != null) {
            it.setAction(event.actionText, event.action)
        }
    }.show()
}

