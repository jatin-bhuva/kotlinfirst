package com.example.kotliin1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Locale

class Lesson4S1 : AppCompatActivity() {

    private lateinit var fullNameLayout: LinearLayout
    private lateinit var phoneNumberLyout: LinearLayout
    private lateinit var emailLayout: LinearLayout
    private lateinit var countryLayout: LinearLayout
    private lateinit var addressLayout: LinearLayout
    private lateinit var dateOfBirthLayout: LinearLayout

    private lateinit var dateOfBirth: EditText
    private lateinit var fullName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var email: EditText
    private lateinit var country: EditText
    private lateinit var address: EditText
    private lateinit var autoComplete1: AutoCompleteTextView
    private lateinit var autoComplete2: AutoCompleteTextView
    private lateinit var seekBarHSC: SeekBar
    private lateinit var selectedPercentageHSC: TextView
    private lateinit var seekBarUG: SeekBar
    private lateinit var selectedPercentageUG: TextView
    private lateinit var validateButton: Button

    private lateinit var languageSpinner: Spinner
    private val today = MaterialDatePicker.todayInUtcMilliseconds()
    private val constraintsBuilder = CalendarConstraints.Builder()
        .setValidator(DateValidatorPointBackward.now())
    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setCalendarConstraints(constraintsBuilder.build())
        .setSelection(today)
        .build()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lesson4_s1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initializeVariables()
        val language = getSavedLanguagePreference()
        languageSpinner.setSelection(if (language == "en") 0 else 1)

        seekBarHSC.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedPercentageHSC.text = "$progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        seekBarUG.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                selectedPercentageUG.text = "$progress%"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        dateOfBirth.setOnClickListener() {
            datePicker.show(supportFragmentManager, "DATE_PICKER")
            datePicker.addOnPositiveButtonClickListener {
                dateOfBirth.setText(datePicker.headerText)
                dateOfBirth.setError(null)
            }
        }

        var check = 0;
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (++check > 1) {
                    when (position) {
                        0 -> changeLanguage(this@Lesson4S1, "en")
                        1 -> changeLanguage(this@Lesson4S1, "hi")

                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        validateButton.setOnClickListener {


            val userName = fullName.text.toString().trim()
            val emailText = email.text.toString().trim()
            val contactNumberText = phoneNumber.text.toString().trim()
            val countryText = country.text.toString().trim()
            val addressText = address.text.toString().trim()
            val dateOfBirthText = dateOfBirth.text
            fun setError(field: EditText, errorMessage: String) {
                field.error = errorMessage
            }

            when {
                userName.isEmpty() -> setError(fullName, getString(R.string.enter_user_name))

                contactNumberText.isEmpty() -> phoneNumber.setError(getString(R.string.enter_contact))
                !contactNumberText.matches("^[0-9]+$".toRegex()) || contactNumberText.length != 10 -> setError(
                    phoneNumber,
                    getString(R.string.invalid_contact)
                )

                emailText.isEmpty() -> setError(email, getString(R.string.enter_email))
                !android.util.Patterns.EMAIL_ADDRESS.matcher(emailText).matches() -> setError(
                    email,
                    getString(R.string.invalid_email)
                )

                countryText.isEmpty() -> setError(country, getString(R.string.enter_contact))

                addressText.isEmpty() -> setError(address, getString(R.string.enter_contact))

                dateOfBirthText.isEmpty() -> setError(dateOfBirth, getString(R.string.enter_email))

                else -> setError(dateOfBirth, "")

            }

        }
    }

    private fun initializeVariables() {
        fullNameLayout = findViewById(R.id.full_name)
        phoneNumberLyout = findViewById(R.id.phone_no)
        emailLayout = findViewById(R.id.email)
        countryLayout = findViewById(R.id.country)
        addressLayout = findViewById(R.id.address)
        dateOfBirthLayout = findViewById(R.id.date_of_birth)
        autoComplete1 = findViewById(R.id.autoCompleteTextView1)
        autoComplete2 = findViewById(R.id.autoCompleteTextView2)
        seekBarHSC = findViewById(R.id.percentageSeekBar)
        selectedPercentageHSC = findViewById(R.id.selectedPercentage)
        seekBarUG = findViewById(R.id.ugPercentageSeekBar)
        selectedPercentageUG = findViewById(R.id.ugSelectedPercentage)
        languageSpinner = findViewById(R.id.languageSpinner)
        validateButton = findViewById(R.id.validateBtn)
        assignAttriToInput()
    }

    private fun assignAttriToInput() {
        val qualifications = listOf(getString(R.string.ssc), getString(R.string.hsc))
        val ugQualifications = listOf(getString(R.string.bca), getString(R.string.bcom))
        val adapter1 =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, qualifications)
        autoComplete1.setAdapter(adapter1)

        val adapterGrad =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, ugQualifications)
        autoComplete2.setAdapter(adapterGrad)

        fullNameLayout.findViewById<ImageView>(R.id.input_icon).setImageResource(R.drawable.user)
        fullName = fullNameLayout.findViewById<EditText>(R.id.input_field)
        fullName.apply {
            hint = getString(R.string.full_name)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        phoneNumberLyout.findViewById<ImageView>(R.id.input_icon).setImageResource(R.drawable.phone)
        phoneNumber = phoneNumberLyout.findViewById<EditText>(R.id.input_field)
        phoneNumber.apply {
            hint = getString(R.string.phone_number)
            inputType = InputType.TYPE_CLASS_PHONE
        }

        emailLayout.findViewById<ImageView>(R.id.input_icon).setImageResource(R.drawable.email)
        email = emailLayout.findViewById<EditText>(R.id.input_field)
        email.apply {
            hint = getString(R.string.email)
            inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        }

        countryLayout.findViewById<ImageView>(R.id.input_icon).setImageResource(R.drawable.country)
        country = countryLayout.findViewById<EditText>(R.id.input_field)
        country.apply {
            hint = getString(R.string.country)
            inputType = InputType.TYPE_CLASS_TEXT
        }

        addressLayout.findViewById<ImageView>(R.id.input_icon).setImageResource(R.drawable.address)
        address = addressLayout.findViewById<EditText>(R.id.input_field)
        address.apply {
            hint = getString(R.string.address)
            inputType = InputType.TYPE_CLASS_TEXT
        }


        dateOfBirthLayout.findViewById<ImageView>(R.id.input_icon).setImageResource(R.drawable.date)
        dateOfBirth = dateOfBirthLayout.findViewById(R.id.input_field)
        dateOfBirth.apply {
            hint = getString(R.string.date_of_birth)
            inputType = InputType.TYPE_NULL
            isFocusable = false
            isClickable = true
            isLongClickable = false
        }
    }

    fun changeLanguage(context: Context, languageCode: String) {
        saveLanguagePreference(languageCode)
        val intent = Intent(context, Lesson4S1::class.java)
        context.startActivity(intent)
        (context as Activity).finish()
    }

    private fun saveLanguagePreference(languageCode: String) {
        val sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        sharedPref.edit().putString("Language", languageCode).apply()
    }

    private fun getSavedLanguagePreference(): String {
        val sharedPref = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPref.getString("Language", "en") ?: "en"
    }

    override fun attachBaseContext(newBase: Context) {
        val savedLanguage = getSavedLanguagePreference(newBase)
        val context = updateLocale(newBase, savedLanguage)
        super.attachBaseContext(context)
    }

    private fun getSavedLanguagePreference(context: Context): String {
        val sharedPref = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        return sharedPref.getString("Language", "en") ?: "en"
    }

    private fun updateLocale(context: Context, languageCode: String): Context {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

}