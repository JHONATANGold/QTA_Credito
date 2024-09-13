package com.example.qtacredito

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.NumberFormat
import java.util.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Obtener referencias a los EditTexts
        val valorPropiedadEditText = findViewById<EditText>(R.id.editTextNumber)
        val cuantoNecesitasEditText = findViewById<EditText>(R.id.editTextNumber2)
        val plazoEditText = findViewById<EditText>(R.id.editTextNumber3)
        val tasaInteresEditText = findViewById<EditText>(R.id.editTextNumber4)

        // Obtener referencia al TextView donde se mostrará el resultado
        val resultadoTextView = findViewById<TextView>(R.id.textView12)

        // Configurar el botón
        val simularButton = findViewById<Button>(R.id.button)
        simularButton.setOnClickListener {
            try {
                // Capturar los valores ingresados
                val valorPropiedad = valorPropiedadEditText.text.toString().toDouble()
                val cuantoNecesitas = cuantoNecesitasEditText.text.toString().toDouble()
                val plazo = plazoEditText.text.toString().toInt()
                val tasaInteres = tasaInteresEditText.text.toString().toDouble()

                // Validaciones
                if (valorPropiedad < 70000000) {
                    Toast.makeText(this, "El valor de la propiedad no puede ser inferior a $70.000.000", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (cuantoNecesitas < 50000000 || cuantoNecesitas > valorPropiedad * 0.7) {
                    Toast.makeText(this, "El préstamo debe ser entre $50.000.000 y el 70% del valor de la propiedad", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (plazo < 5 || plazo > 20) {
                    Toast.makeText(this, "El plazo debe estar entre 5 y 20 años", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                if (tasaInteres < 12.0 || tasaInteres > 24.9) {
                    Toast.makeText(this, "La tasa de interés debe estar entre 12.0 y 24.9", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // Cálculo de la cuota mensual
                val i = (tasaInteres / 12) / 100
                val n = plazo * 12
                val cuotaMensual = cuantoNecesitas * (1 + i).pow(n.toDouble()) * i / ((1 + i).pow(n.toDouble()) - 1)

                // Formatear el resultado sin decimales y con separación de miles
                val cuotaMensualFormateada = NumberFormat.getNumberInstance(Locale.US).format(cuotaMensual.toInt())

                // Mostrar el resultado en el TextView con el texto solicitado
                resultadoTextView.text = "Paga cuotas de $$cuotaMensualFormateada por mes"

            } catch (e: Exception) {
                Toast.makeText(this, "Por favor ingrese todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
