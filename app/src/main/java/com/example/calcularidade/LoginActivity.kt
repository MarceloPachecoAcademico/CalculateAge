package com.example.calcularidade

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuario1: EditText
    private lateinit var etSenha1: EditText
    private lateinit var btAcessar1: Button
    private lateinit var btSairLogin1: Button
    private lateinit var btBiometria1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        etUsuario1 = findViewById(R.id.etUsuario)
        etSenha1 = findViewById(R.id.etSenha)
        btAcessar1 = findViewById(R.id.btAcessar)
        btSairLogin1 = findViewById(R.id.btSairLogin)
        btBiometria1 = findViewById(R.id.btBiometria)

        val telaMenu = Intent(this, MenuActivity::class.java)

        btBiometria1.setOnClickListener {
            if (isBiometricAvailable()) {
                showBiometricPrompt(
                    onSuccess = {
                        startActivity(telaMenu)
                    },
                    onFailure = {
                        Toast.makeText(this, "Autenticação biométrica falhou.", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                // Se a biometria não estiver cadastrada/disponível, redireciona para a tela de configurações ou permite login direto
                promptEnrollBiometric()
            }
        }

        btAcessar1.setOnClickListener{
            if (etUsuario1.text.toString() == "Marcelo" && etSenha1.text.toString() == "123" ){
                startActivity(telaMenu)
            }
            else{
                Toast.makeText(this, "Usuário e/ou senha inválido(s).", Toast.LENGTH_SHORT).show()
            }

        }


        btSairLogin1.setOnClickListener {
            finishAndRemoveTask()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun isBiometricAvailable(): Boolean {
        val biometricManager = BiometricManager.from(this)
        val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
        return when (biometricManager.canAuthenticate(authenticators)) {
            BiometricManager.BIOMETRIC_SUCCESS -> true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("BiometricAuth", "Nenhum hardware biométrico disponível.")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                Log.d("BiometricAuth", "Hardware biométrico indisponível.")
                false
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                Log.d("BiometricAuth", "Nenhuma biometria cadastrada.")
                false
            }
            else -> false
        }
    }

    private fun promptEnrollBiometric() {
        try {
            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                )
            }
            startActivity(enrollIntent)
        } catch (e: ActivityNotFoundException) {
            val fallbackIntent = Intent(Settings.ACTION_SECURITY_SETTINGS)
            startActivity(fallbackIntent)
        }
    }

    private fun showBiometricPrompt(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        val executor = ContextCompat.getMainExecutor(this)

        val biometricPrompt = BiometricPrompt(
            this,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.d("BiometricAuth", "Erro biométrico ($errorCode): $errString")
                    onFailure()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.d("BiometricAuth", "Autenticação biométrica realizada com sucesso!")
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.d("BiometricAuth", "Falha na autenticação biométrica.")
                    onFailure()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticação Biométrica")
            .setSubtitle("Acesse o aplicativo usando sua biometria")
            .setNegativeButtonText("Cancelar")
            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
