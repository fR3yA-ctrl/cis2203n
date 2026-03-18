package com.example.exercise_03;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity started");

        EditText editMessage = findViewById(R.id.editMessage);
        Button btnSend = findViewById(R.id.btnSend);
        Button btnShare = findViewById(R.id.btnShare);

        btnSend.setOnClickListener(v -> {
            String message = editMessage.getText().toString();

            Log.d(TAG, "Send button clicked. Message: " + message);

            Intent intent = new Intent(MainActivity.this, EchoActivity.class);
            intent.putExtra("MESSAGE_KEY", message);
            startActivity(intent);
        });

        // Bonus: Implicit Intent to share message via SMS, email, etc.
        btnShare.setOnClickListener(v -> {
            String message = editMessage.getText().toString();

            Log.d(TAG, "Share button clicked. Message: " + message);

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(shareIntent, "Share message via"));
        });
    }
}