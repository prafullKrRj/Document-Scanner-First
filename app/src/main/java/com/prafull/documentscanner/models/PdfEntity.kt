package com.prafull.documentscanner.models

import java.util.Date


data class PdfEntity(
        val id: String,
        val name: String,
        val size: String,
        val lastModified: Date,
)
