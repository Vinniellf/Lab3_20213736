package com.example.teletrivia;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;


public class QuestionActivity extends AppCompatActivity {

    private TextView tvTiempo, tvPregunta, tvTextPregunta;
    private RadioGroup radioGroup;
    private RadioButton optionTrue, optionFalse;
    private Button btnSiguiente;
    private int tiempoRestante; // Tiempo inicial (en segundos)
    private boolean respuestaSeleccionada = false;
    private CountDownTimer countDownTimer;
    private int preguntaActual = 0;
    private int totalPreguntas;
    private int correctas = 0;
    private int incorrectas = 0;

    ArrayList<Question> questions = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        // Inicializamos las vistas
        tvTiempo = findViewById(R.id.tv_tiempo);
        tvPregunta = findViewById(R.id.tv_pregunta);
        tvTextPregunta = findViewById(R.id.tvtextpregunta);
        optionTrue = findViewById(R.id.optiontrue);
        optionFalse = findViewById(R.id.optionfalse);
        btnSiguiente = findViewById(R.id.btnsiguiente);
        radioGroup = findViewById(R.id.radioGroup);


        // Primera vez entrando, recibimos preguntas
        if (questions == null) {
            Intent intent = getIntent();
            questions = intent.getParcelableArrayListExtra("pre");
            if (questions != null && !questions.isEmpty()) {
                String dificultad = questions.get(0).getDificultad();
                //String categoria = viewModel.questions.get(0).getCategory();
                if ("easy".equals(dificultad)) {
                    tiempoRestante = 5000;  // 5 segundos por pregunta
                } else if ("medium".equals(dificultad)) {
                    tiempoRestante = 7000;  // 7 segundos por pregunta
                } else if ("hard".equals(dificultad)) {
                    tiempoRestante = 10000;  // 10 segundos por pregunta
                } else {
                    // En caso de que no se reciba una dificultad válida, se asigna un valor por defecto
                    tiempoRestante = 5000;  // 5 segundos por pregunta
                }
                totalPreguntas = questions.size();
                tiempoRestante = totalPreguntas * tiempoRestante;


                iniciarTemporizador();
                siguientePregunta();
            } else {
                Toast.makeText(this, "No se recibieron preguntas", Toast.LENGTH_SHORT).show();
            }
        }




        // Configuración del botón "Siguiente"
        btnSiguiente.setOnClickListener(v -> {
            if (respuestaSeleccionada) {
                Question currentQuestion = questions.get(preguntaActual - 1);
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);
                String selectedAnswer = selectedRadioButton.getText().toString();
                if(currentQuestion.getCorrect_answer().equals(selectedAnswer)) correctas++;
                else incorrectas++;
                siguientePregunta();
            } else {
                Toast.makeText(QuestionActivity.this, "Por favor, seleccione una respuesta", Toast.LENGTH_SHORT).show();
            }
        });

        // Escuchamos si el usuario seleccionó una respuesta
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            respuestaSeleccionada = true;
            btnSiguiente.setEnabled(true); // Habilitar el botón Siguiente
        });


    }

    private void iniciarTemporizador() {
        countDownTimer = new CountDownTimer(tiempoRestante, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int segundosRestantes = (int) (millisUntilFinished / 1000);
                tvTiempo.setText("Tiempo restante: " + String.format("%02d:%02d", segundosRestantes / 60, segundosRestantes % 60));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(QuestionActivity.this, ResultadosActivity.class);
                intent.putExtra("correctas", correctas);
                intent.putExtra("incorrectas", incorrectas);
                intent.putExtra("nulas", totalPreguntas - correctas - incorrectas);


                startActivity(intent);
            }
        };
        countDownTimer.start();
    }

    private void siguientePregunta() {
        resetearCampos();
        Question currentQuestion = questions.get(preguntaActual);
        tvPregunta.setText(currentQuestion.getQuestion());
        if (preguntaActual < totalPreguntas) {
            preguntaActual++;
            // Actualizamos la pregunta y el contador
            tvPregunta.setText("Pregunta " + preguntaActual + "/" + totalPreguntas);
            tvTextPregunta.setText(currentQuestion.getQuestion());
        } else {

            Intent intent = new Intent(QuestionActivity.this, ResultadosActivity.class);
            intent.putExtra("correctas", correctas);
            intent.putExtra("incorrectas", incorrectas);
            intent.putExtra("nulas", totalPreguntas - correctas - incorrectas);


            startActivity(intent);
        }
    }

    private void resetearCampos() {
        respuestaSeleccionada = false;
        optionTrue.setChecked(false);
        optionFalse.setChecked(false);
        btnSiguiente.setEnabled(false);

      // Reiniciar el temporizador
    }

    private void mostrarEstadisticas() {
        // Aquí puedes redirigir a otra actividad con las estadísticas del juego
        Toast.makeText(QuestionActivity.this, "Juego Finalizado", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel(); // Cancelar el temporizador al destruir la actividad
        }
    }
}
