package com.ifsc.app;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Integer contador = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main); /// constraint, button e Textview

        View telaPrincipal = findViewById(R.id.main);
        telaPrincipal.setBackgroundColor(Color.RED);

        TextView tv = findViewById(R.id.text);
        tv.setText("melao");
        tv.setTextColor(Color.WHITE);

        Button btn = findViewById(R.id.button);
        btn.setText("ta doce?");
        btn.setBackgroundColor(Color.BLACK);
        btn.setOnClickListener(v -> {
            contador++;
            tv.setText("tem "  + Integer.toString(contador) + " risquinho");
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}