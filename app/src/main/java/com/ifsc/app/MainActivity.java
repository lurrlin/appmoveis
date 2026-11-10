package com.ifsc.app;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        PaintView paintView = findViewById(R.id.paintView);

        Button btnFreehand = findViewById(R.id.btnFreehand);
        Button btnRectangle = findViewById(R.id.btnRectangle);
        Button btnCircle = findViewById(R.id.btnCircle);

        Button btnColorBlack = findViewById(R.id.btnColorBlack);
        Button btnColorRed = findViewById(R.id.btnColorRed);
        Button btnColorBlue = findViewById(R.id.btnColorBlue);
        Button btnColorGreen = findViewById(R.id.btnColorGreen);
        Button btnClear = findViewById(R.id.btnClear);

        btnFreehand.setOnClickListener(v -> paintView.setDrawMode(PaintView.DrawMode.FREEHAND));
        btnRectangle.setOnClickListener(v -> paintView.setDrawMode(PaintView.DrawMode.RECTANGLE));
        btnCircle.setOnClickListener(v -> paintView.setDrawMode(PaintView.DrawMode.CIRCLE));

        btnColorBlack.setOnClickListener(v -> paintView.setDrawingColor(Color.BLACK));
        btnColorRed.setOnClickListener(v -> paintView.setDrawingColor(Color.RED));
        btnColorBlue.setOnClickListener(v -> paintView.setDrawingColor(Color.BLUE));
        btnColorGreen.setOnClickListener(v -> paintView.setDrawingColor(Color.GREEN));

        btnClear.setOnClickListener(v -> paintView.clear());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
