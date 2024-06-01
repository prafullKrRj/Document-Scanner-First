package com.prafull.documentscanner.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "pdf_table")
data class PdfEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val size: String,
    val lastModified: Date,
)
