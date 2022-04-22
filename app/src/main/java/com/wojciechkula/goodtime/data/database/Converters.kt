package com.wojciechkula.goodtime.data.database

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromArrayListToDoubles(list: ArrayList<Double>?): String {
        return list?.joinToString(separator = ";") { it.toString() } ?: ""
    }

    @TypeConverter
    fun toArrayListOfDoubles(string: String) : ArrayList<Double> {
        return ArrayList(string?.split(";")?.mapNotNull { it.toDoubleOrNull() } ?: emptyList())
    }
}
