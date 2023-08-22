package com.example.myapplication

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Utils {
    fun convertToDate(dt: String?): String {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ", Locale.getDefault())
        var date: Date? = Date()
        val displayDate: String
        val newDateFormatter = SimpleDateFormat("yyyy", Locale.ENGLISH)
        try {
            date = dateFormatter.parse(dt)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        displayDate = newDateFormatter.format(date)
        return displayDate
    }
}