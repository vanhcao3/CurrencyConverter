package com.example.currencyconverter

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "VND" to 25355.05,
        "GBP" to 0.77,
        "EUR" to 0.92,
        "JPY" to 153.00
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currencies = exchangeRates.keys.toList()

        val spinnerFrom: Spinner = findViewById(R.id.fromCurrencySpinner)
        val spinnerTo: Spinner = findViewById(R.id.toCurrencySpinner)
        val inputAmount: EditText = findViewById(R.id.amountInput)
        val resultText: TextView = findViewById(R.id.resultText)
        val convertButton: Button = findViewById(R.id.convertButton)

        // Populate spinners
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        convertButton.setOnClickListener {
            val fromCurrency = spinnerFrom.selectedItem.toString()
            val toCurrency = spinnerTo.selectedItem.toString()
            val amount = inputAmount.text.toString().toDoubleOrNull()

            if (amount != null && exchangeRates.containsKey(fromCurrency) && exchangeRates.containsKey(toCurrency)) {
                val result = convertCurrency(amount, fromCurrency, toCurrency)
                resultText.text = "$result $toCurrency"
            } else {
                Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0
        return amount * (toRate / fromRate)
    }
}
