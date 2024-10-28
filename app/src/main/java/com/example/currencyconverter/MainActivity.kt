package com.example.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val exchangeRates = mapOf(
        "USD" to 1.0,    // Base currency (USD)
        "VND" to 23175.0,
        "GBP" to 0.77,
        "EUR" to 0.90,
        "JPY" to 108.61
    )

    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var inputAmount: EditText
    private lateinit var resultText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currencies = exchangeRates.keys.toList()

        spinnerFrom = findViewById(R.id.fromCurrencySpinner)
        spinnerTo = findViewById(R.id.toCurrencySpinner)
        inputAmount = findViewById(R.id.amountInput)
        resultText = findViewById(R.id.resultText)

        // Populate spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        // Add listeners for input changes
        inputAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateResult()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateResult()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                updateResult()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun updateResult() {
        val fromCurrency = spinnerFrom.selectedItem.toString()
        val toCurrency = spinnerTo.selectedItem.toString()
        val amount = inputAmount.text.toString().toDoubleOrNull()

        if (amount != null && exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
            val result = convertCurrency(amount, fromCurrency, toCurrency)
            resultText.text = "$result $toCurrency"
        } else {
            resultText.text = "Result"
        }
    }

    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0
        return amount * (toRate / fromRate)
    }
}
