package com.example.bmicalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.view.View
import android.view.inputmethod.InputMethodManager

class MainScreen : AppCompatActivity() {
    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val calcButton = findViewById<Button>(R.id.calculate)
        val result = findViewById<TextView>(R.id.result)

        calcButton.setOnClickListener {
            val view: View? = this.currentFocus
            if (view != null) {
                // on below line we are creating a variable
                // for input manager and initializing it.
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                // on below line hiding our keyboard.
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }

            val weightInput: Int? = findViewById<EditText>(R.id.weight).text.toString().toIntOrNull()
            val heightInFtInput: Int? = findViewById<EditText>(R.id.heightInFt).text.toString().toIntOrNull()
            val heightInInchInput: Int? = findViewById<EditText>(R.id.heightInInch).text.toString().toIntOrNull()

            if (weightInput == null || heightInFtInput == null || heightInInchInput == null) {
                // Handle the case where inputs are null (ideally show an error or handle gracefully)
                result.text = "Invalid input"
                return@setOnClickListener
            }

            val heightInMeters: Double = (heightInFtInput * 0.3048) + (heightInInchInput * 0.0254)
            val totalBMI: Double = weightInput / (heightInMeters * heightInMeters)
            val formattedBMI = String.format("%.1f", totalBMI)

            if (totalBMI < 18.5){
                result.text = "Your BMI is ${formattedBMI} \nand\n You are Underweight"
            }else if(totalBMI > 25){
                result.text = "Your BMI is ${formattedBMI} \nand\n You are Overweight"
            }else{
                result.text = "Your BMI is ${formattedBMI} \nand\n You are Normal Body weight"
            }
        }
    }
}