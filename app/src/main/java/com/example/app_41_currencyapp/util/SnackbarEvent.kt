package com.example.app_41_currencyapp.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

class SnackbarEvent(val text: String, val length: Int = Snackbar.LENGTH_SHORT, val actionText: String? = null, val action: ((View) -> Unit)? = null)