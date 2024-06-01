package com.prafull.documentscanner.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.documentscanner.data.models.PdfEntity
import com.prafull.documentscanner.data.repository.PdfRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Appendable

class PdfViewModel(application: Application) : ViewModel() {

    private val pdfRepository = PdfRepository(application)
    var isSplashScreenVisible by mutableStateOf(true)
    var showRenameDialog by mutableStateOf(false)

    private val _pdfStateFlow = MutableStateFlow(emptyList<PdfEntity>())
    val pdfStateFlow = _pdfStateFlow.asStateFlow()

    var currPdfEntity: PdfEntity? by mutableStateOf(null)
    init {
        viewModelScope.launch {
            delay(1500)
            isSplashScreenVisible = false
        }
        getAllPdfs()
    }
    private fun getAllPdfs() {
        viewModelScope.launch(Dispatchers.IO) {
            pdfRepository.getAllPdfs().collect { resp ->
                _pdfStateFlow.update {
                    resp
                }
            }
        }
    }
    fun insertPdf(pdfEntity: PdfEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = pdfRepository.insertPdf(pdfEntity)
        }
    }
    fun deletePdf(pdfEntity: PdfEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = pdfRepository.deletePdf(pdfEntity)
        }
    }
    fun updatePdf(pdfEntity: PdfEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = pdfRepository.updatePdf(pdfEntity)
        }
    }
}