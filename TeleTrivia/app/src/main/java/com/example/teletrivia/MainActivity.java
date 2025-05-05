package com.example.teletrivia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.EditText;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Spinner;
import android.net.Network;
import android.net.NetworkCapabilities;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MainActivity extends AppCompatActivity {

    String[] list_categoria = {"", "Cultura General", "Libros", "Películas", "Música", "Computación", "Matemática", "Historia", "Deportes"};
    String[] list_dificultad = {"", "fácil", "medio", "difícil"};


    AutoCompleteTextView autoCompleteTextView1, autoCompleteTextView2;
    ArrayAdapter<String> arrayAdapter_categoria, arrayAdapter_dificultad;
    private MainActivity binding;
    private Button botonComenzar;
    private EditText cantidadEditText;
    private TextInputLayout categoriaInputLayout, dificultadInputLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        autoCompleteTextView1 = findViewById(R.id.auto_complete_txt1);
        autoCompleteTextView2 = findViewById(R.id.auto_complete_txt_di);
        botonComenzar = findViewById(R.id.button_start_game);
        cantidadEditText = findViewById(R.id.editText_quantity);
        categoriaInputLayout = findViewById(R.id.categoria_input_layout);
        dificultadInputLayout = findViewById(R.id.dificultad_input_layout);


        arrayAdapter_categoria = new ArrayAdapter<String>(this, R.layout.list_categoria, list_categoria);
        arrayAdapter_dificultad = new ArrayAdapter<String>(this, R.layout.list_dificultad, list_dificultad);


        autoCompleteTextView1.setAdapter(arrayAdapter_categoria);
        autoCompleteTextView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTextView2.setAdapter(arrayAdapter_dificultad);
        autoCompleteTextView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Item: "+item, Toast.LENGTH_SHORT).show();
            }
        });

        Button botonComprobar = findViewById(R.id.button_check_internet); // Asume que el ID del botón es 'boton_comprobar'
        botonComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobarConexion();
            }
        });

        botonComenzar.setOnClickListener(v -> {
            if (validarFormulario()) {
                String inputText = cantidadEditText.getText().toString();
                String dif = dificultadInputLayout.getEditText().getText().toString();
                String cat = categoriaInputLayout.getEditText().getText().toString();
                Integer cantPreg = Integer.parseInt(inputText);
                // Si las validaciones son correctas, hacer algo, como iniciar el juego
                Toast.makeText(this, "Formulario válido, comenzando...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);

                // Parámetros de ejempl
                int categoria = Categoria(cat);
                String dificultad = Dificultad(dif);
                String type = "boolean";

                ApiService apiService = RetrofitClient.getApiService();
                Call<QuestionResponse> call = apiService.getQuestions(cantPreg, categoria, dificultad, type);

                call.enqueue(new Callback<QuestionResponse>() {
                    @Override
                    public void onResponse(Call<QuestionResponse> call, Response<QuestionResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            ArrayList<Question> questions = response.body().getResults();
                            if(questions == null){
                                Intent intent2 = new Intent(MainActivity.this, MainActivity.class);

                                startActivity(intent2);
                            } else {
                                intent.putParcelableArrayListExtra("pre", (ArrayList<? extends Parcelable>) questions);
                                startActivity(intent);
                            }
                            // Iniciamos la nueva actividad
                        } else {
                            // Manejar error de respuesta
                            Log.e("Error", "No se recibieron preguntas.");
                        }
                    }

                    @Override
                    public void onFailure(Call<QuestionResponse> call, Throwable t) {
                        // Manejar error de la solicitud
                        Log.e("Fallo", t.getMessage());
                    }
                });

            } else {
                // Si alguna validación falla, muestra un mensaje de error
                Toast.makeText(this, "Por favor, completa todos los campos correctamente.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void comprobarConexion() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            Network network = cm.getActiveNetwork();

            if (network != null) {
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(network);

                if (capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                    Toast.makeText(this, "Conexión exitosa", Toast.LENGTH_SHORT).show();

                    botonComenzar.setEnabled(true);
                } else {
                    Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Error de conexión", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarFormulario() {
        // Obtener la cantidad como entero
        String cantidadString = cantidadEditText.getText().toString();
        int cantidad = cantidadString.isEmpty() ? 0 : Integer.parseInt(cantidadString);
        boolean ok = true;
        if (cantidad <= 0) {
            cantidadEditText.setError("La cantidad debe ser un número positivo.");
            ok = false;
        }

        String categoria = autoCompleteTextView1.getText().toString();
        String dificultad = autoCompleteTextView2.getText().toString();

        // Verificar que no esté vacío (si es necesario validar que no sea la opción por defecto)
        if (categoria.isEmpty()) {
            categoriaInputLayout.setError("Por favor seleccione una categoría.");
            ok = false;
        } else {
            categoriaInputLayout.setError(null); // Eliminar el error si es válido
        }

        if (dificultad.isEmpty()) {
            dificultadInputLayout.setError("Por favor seleccione una dificultad.");
            ok = false;
        } else {
            dificultadInputLayout.setError(null); // Eliminar el error si es válido
        }

        // Si todas las validaciones pasan
        return ok;
    }

    private int Categoria(String categoria) {
        switch (categoria) {
            case "Cultura General": return 9;
            case "Libros": return 10;
            case "Películas": return 11;
            case "Música": return 12;
            case "Computación": return 18;
            case "Matemática": return 19;
            case "Historia": return 21;
            case "Deportes": return 23;
            default: return 9;
        }
    }

    private String  Dificultad(String dificultad) {
        switch (dificultad) {
            case "fácil": return "easy";
            case "medio": return "medium";
            case "difícil": return "hard";

            default: return "Easy";
        }
    }


}