package com.prafull.documentscanner.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverters {
    @TypeConverter
    fun fromTimestamp(value: Long): Date {
        return Date(value)
    }
    @TypeConverter
    fun dateToTimestamp(date: Date): Long {
        return date.time
    }
}