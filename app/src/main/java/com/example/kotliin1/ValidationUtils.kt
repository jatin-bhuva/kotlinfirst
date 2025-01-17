package com.example.kotliin1

import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

object ValidationUtils {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPhone(phone: String): Boolean {
        return phone.matches(Regex(Constants.PHONE_REGEX))
    }

    fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.US)
        dateFormat.isLenient = false
        return try {
            dateFormat.parse(date)
            true
        } catch (e: ParseException) {
            false
        }
    }
}
