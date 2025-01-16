package com.example.kotliin1

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applyStatusAndActionBarColor()
    }

    override fun onResume() {
        super.onResume()
        applyStatusAndActionBarColor()
    }

    private fun applyStatusAndActionBarColor() {
        val savedStatusBarColor = getColorFromPreferences(Constants.KEY_STATUS_BAR_COLOR)
        val savedActionBarColor = getColorFromPreferences(Constants.KEY_ACTION_BAR_COLOR)
        applyColorToActionBar(savedActionBarColor)

        window.decorView.post {
            applyColorToStatusBar(savedStatusBarColor)
        }
    }

    public fun getColorFromPreferences(
        key: String,
        defaultColor: String = Constants.WHITE
    ): String {
        val sharedPreferences = getSharedPreferences(Constants.SETTING_PREFS_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(key, defaultColor) ?: defaultColor
    }

    private fun applyColorToStatusBar(color: String) {
        val colorInt = returnColorInt(color)
        window.statusBarColor = colorInt
    }

    private fun applyColorToActionBar(color: String) {
        val colorInt = returnColorInt(color)
        supportActionBar?.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(colorInt))
    }

    private fun returnColorInt(color: String): Int {
        val colorInt = when (color) {
            Constants.BLUE -> Color.BLUE
            Constants.GREEN -> Color.GREEN
            Constants.RED -> Color.RED
            else -> Color.WHITE
        }
        return colorInt
    }
}
