package com.example.hellocampus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private int mCounter = 0;
    private TextView counterDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // FIXED SABOTAGE
        counterDisplay = findViewById(R.id.tvCounter);
        counterDisplay.setText("0");

        Button btnIncrement = findViewById(R.id.btnIncrement);

        btnIncrement.setOnClickListener(v -> {
            mCounter++;
            counterDisplay.setText(String.valueOf(mCounter));
        });
    }

    // SAVE counter before rotation destroys activity
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("COUNT_KEY", mCounter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mCounter = savedInstanceState.getInt("COUNT_KEY");
        counterDisplay.setText(String.valueOf(mCounter));
    }
}