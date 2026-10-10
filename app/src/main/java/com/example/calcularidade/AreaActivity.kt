package com.example.calcularidade

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AreaActivity : AppCompatActivity() {
    private lateinit var etBase1 : EditText
    private lateinit var etAltura1 : EditText
    private lateinit var tvArea1 : TextView
    private lateinit var btCalcular1 : Button
    private lateinit var btLimpar1 : Button
    private lateinit var btSair1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_area)

        etBase1 = findViewById(R.id.etBase)
        etAltura1 = findViewById(R.id.etAltura)
        tvArea1 = findViewById(R.id.tvarea)
        btCalcular1 = findViewById(R.id.btCalcular)
        btLimpar1 = findViewById(R.id.btLimpar)
        btSair1 = findViewById(R.id.btSair)

        btCalcular1.setOnClickListener {



            val base : Double = etBase1.text.toString().toDouble()
            val altura : Double = etAltura1.text.toString().toDouble()
            val area : Double = base * altura
            tvArea1.text = area.toString()
        }

        btLimpar1.setOnClickListener {
            etBase1.setText("")
            etAltura1.setText("")
            tvArea1.text = ""
        }
        btSair1.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}