package com.ar.practice.utils

import com.ar.practice.data.model.Transaction
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
    return sdf.format(date)
}

fun isToday(dateString: String): Boolean {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    val date = sdf.parse(dateString) ?: return false

    val calendar = Calendar.getInstance()
    val todayYear = calendar.get(Calendar.YEAR)
    val todayMonth = calendar.get(Calendar.MONTH)
    val todayDay = calendar.get(Calendar.DAY_OF_MONTH)

    calendar.time = date
    val dateYear = calendar.get(Calendar.YEAR)
    val dateMonth = calendar.get(Calendar.MONTH)
    val dateDay = calendar.get(Calendar.DAY_OF_MONTH)

    return todayYear == dateYear && todayMonth == dateMonth && todayDay == dateDay
}

fun sortTransactionsByDateDescending(transactions: List<Transaction>): List<Transaction> {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)

    val transactionsWithDates = transactions.mapNotNull { transaction ->
        try {
            val date = sdf.parse(transaction.date)
            date?.let { it to transaction }
        } catch (e: Exception) {
            null
        }
    }

    val sortedTransactions = transactionsWithDates.sortedByDescending { it.first }

    return sortedTransactions.map { it.second }
}