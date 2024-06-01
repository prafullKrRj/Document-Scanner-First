package com.prafull.documentscanner.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.prafull.documentscanner.data.models.PdfEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PdfDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPdf(pdfEntity: PdfEntity) : Long

    @Delete
    suspend fun deletePdf(pdfEntity: PdfEntity) : Int

    @Update
    suspend fun updatePdf(pdfEntity: PdfEntity) : Int

    @Query("SELECT * FROM pdf_table")
    fun getAllPdfs() : Flow<List<PdfEntity>>
}