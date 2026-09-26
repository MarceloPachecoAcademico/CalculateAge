package com.example.calcularidade

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var etAnoAtual1 : EditText
    private lateinit var etDtNasc1 : EditText
    private lateinit var btCalcular1 : Button
    private lateinit var btLimpar1 : Button
    private lateinit var btSair1 : Button
    private lateinit var tvAnos1 : TextView
    private lateinit var tvMeses1 : TextView
    private lateinit var tvDias1 : TextView
    private lateinit var tvHoras1 : TextView
    private lateinit var tvMinutos1 : TextView
    private lateinit var tvSegundos1 : TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        etAnoAtual1 = findViewById(R.id.etAnoAtual)
        etDtNasc1 = findViewById(R.id.etDtNasc)
        btCalcular1 = findViewById(R.id.btCalc)
        btLimpar1 = findViewById(R.id.btLimpar)
        btSair1 = findViewById(R.id.btSair)
        tvAnos1 = findViewById(R.id.tvAnos)
        tvMeses1 = findViewById(R.id.tvMeses)
        tvDias1 = findViewById(R.id.tvDias)
        tvHoras1 = findViewById(R.id.tvHoras)
        tvMinutos1 = findViewById(R.id.tvMinutos)
        tvSegundos1 = findViewById(R.id.tvSegundos)

        btCalcular1.setOnClickListener {

            var dtAtual : String = etAnoAtual1.text.toString()
            var dtNasc : String = etDtNasc1.text.toString()

            var anoAtual : Int = dtAtual.substring(6, 10).toInt()
            var mesAtual : Int = dtAtual.substring(3, 4).toInt()
            var diaAtual : Int = dtAtual.substring(0, 1).toInt()

            var anoNasc : Int = dtNasc.substring(6, 10).toInt()
            var mesNasc : Int = dtNasc.substring(3, 4).toInt()
            var diaNasc : Int = dtNasc.substring(0, 1).toInt()

            var anos : Int = anoAtual - anoNasc

            if (mesNasc > mesAtual || ( mesNasc == mesAtual && diaNasc > diaAtual)){
                anos -= 1
            }

            var meses = anos * 12

            if (mesAtual < mesNasc){
                meses += 12 - mesNasc - mesAtual
                if(diaAtual > diaNasc){
                    meses -= 1
                }
            }
            else{
                meses += mesAtual - mesNasc
            }

            var dias = anos * 365

            if (diaAtual > diaNasc){
                dias += meses % 12 * 30
                dias += diaAtual - diaNasc
            }
            else{
                dias = 30 - (diaAtual - diaNasc)
            }

            var horas = dias * 24
            var minutos = horas * 60
            var segundos = minutos * 60

            tvAnos1.text = anos.toString()
            tvMeses1.text = meses.toString()
            tvDias1.text = dias.toString()
            tvHoras1.text = horas.toString()
            tvMinutos1.text = minutos.toString()
            tvSegundos1.text = segundos.toString()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}