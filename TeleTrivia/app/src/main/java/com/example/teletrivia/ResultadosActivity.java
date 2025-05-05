package com.example.teletrivia;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultadosActivity extends AppCompatActivity {
    private Button btnSiguiente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resultados);
        btnSiguiente = findViewById(R.id.btnsiguiente);
        Intent intent = getIntent();
        int correctas = intent.getIntExtra("correctas", 0);
        int incorrectas = intent.getIntExtra("incorrectas", 0);
        int nulas = intent.getIntExtra("nulas", 0);// Valor por defecto es 0 si no se pasa

        // Usar los datos recibidos
        TextView a1 = findViewById(R.id.tv_pregunta_correcta);
        a1.setText("Correctas: " + correctas);

        TextView a2 = findViewById(R.id.tv_pregunta_incorrecta);
        a2.setText("Incorrectas: " + incorrectas);

        TextView a3 = findViewById(R.id.tv_pregunta_nula);
        a3.setText("No respondidas: " + nulas);

        btnSiguiente.setOnClickListener(v -> {
            Intent intent2 = new Intent(ResultadosActivity.this, MainActivity.class);
            startActivity(intent2);
        });
    }
}