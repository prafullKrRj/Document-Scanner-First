package com.prafull.documentscanner.data.repository

import android.app.Application
import com.prafull.documentscanner.data.local.PdfDatabase
import com.prafull.documentscanner.data.models.PdfEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PdfRepository(application: Application) {
    private val pdfDao = PdfDatabase.getInstance(application).pdfDao()

    suspend fun insertPdf(pdfEntity: PdfEntity) : Long = pdfDao.insertPdf(pdfEntity)
    suspend fun deletePdf(pdfEntity: PdfEntity): Int = pdfDao.deletePdf(pdfEntity)
    suspend fun updatePdf(pdfEntity: PdfEntity) : Int = pdfDao.updatePdf(pdfEntity)

    fun getAllPdfs() : Flow<List<PdfEntity>> = pdfDao.getAllPdfs().flowOn(Dispatchers.IO)
}