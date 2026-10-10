package com.example.calcularidade

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuActivity : AppCompatActivity() {
    private lateinit var btCalcIdade1 : ImageButton
    private lateinit var btCalcArea1 : ImageButton
    private lateinit var btVoltar1 : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        btCalcIdade1 = findViewById(R.id.btCalcIdade)
        btCalcArea1 = findViewById(R.id.btCalcArea)
        btVoltar1 = findViewById(R.id.btVoltar)


        btCalcIdade1.setOnClickListener {
            val telaCalcularIdade = Intent(this, MainActivity::class.java)
            startActivity(telaCalcularIdade)
        }
        btCalcArea1.setOnClickListener {
            val telaCalcularArea = Intent(this, AreaActivity::class.java)
            startActivity(telaCalcularArea)
        }

        btVoltar1.setOnClickListener {
            finish()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
}