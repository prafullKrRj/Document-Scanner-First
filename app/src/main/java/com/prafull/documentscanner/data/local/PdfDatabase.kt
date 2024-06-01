package com.prafull.documentscanner.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prafull.documentscanner.data.local.converters.DateTypeConverters
import com.prafull.documentscanner.data.models.PdfEntity

@Database(entities = [PdfEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateTypeConverters::class)
abstract class PdfDatabase: RoomDatabase() {
    abstract fun pdfDao(): PdfDao
    companion object {
        @Volatile
        private var INSTANCE: PdfDatabase? = null


        // This function is used to get the instance of the database
        fun getInstance(context: Context): PdfDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PdfDatabase::class.java,
                    "pdf_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}