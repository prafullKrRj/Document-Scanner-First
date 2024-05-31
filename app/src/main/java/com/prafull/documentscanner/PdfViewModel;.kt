package com.prafull.documentscanner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PdfViewModel: ViewModel() {


    var isSplashScreenVisible by mutableStateOf(true)
    var showRenameDialog by mutableStateOf(false)
    init {
        viewModelScope.launch {
            delay(3000)
            isSplashScreenVisible = false
        }
    }
}