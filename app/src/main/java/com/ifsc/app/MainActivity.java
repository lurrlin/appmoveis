package com.ifsc.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); /// constraint, button e Textview

        EditText edMin = findViewById(R.id.edMin);
        EditText edMax = findViewById(R.id.edMax);

        TextView tv = findViewById(R.id.text);
        tv.setText("Melão");

        Button btn = findViewById(R.id.button);
        btn.setText("Tem Risquinho?");

        Random rnd = new Random();

        btn.setOnClickListener(v -> {

            String Smin = edMin.getText().toString();
            String Smax = edMax.getText().toString();

            if(Smin.isEmpty()){
                edMin.setError("Informe um Inteiro");
                return;
            }

            if(Smax.isEmpty()){
                edMax.setError("Informe um Inteiro");
                return;
            }

            int min = Integer.parseInt(Smin);
            int max = Integer.parseInt(Smax);

            if (max < min) {
                Toast.makeText(this, "Mínimo deve ser Menor", Toast.LENGTH_SHORT).show();
                return;
            }

            int aleat = rnd.nextInt((max - min) + 1) + min;

            if (aleat == 1) {
                tv.setText("Tem " + aleat + " Risquinho");
            } else {
                tv.setText("Tem " + aleat + " Risquinhos");
            }

            if(aleat<=15){
                btn.setText("Podia ta melhor mano...");
            } else {
                btn.setText("Ta uma doçura papai!");
            }


        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}