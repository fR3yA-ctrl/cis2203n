package com.example.calculator_mockmid;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvDisplay;

    private String operandA = "";
    private String operandB = "";
    private String operator = "";
    private boolean operatorSet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);

        // Number buttons
        int[] numberIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9
        };

        for (int id : numberIds) {
            findViewById(id).setOnClickListener(v -> {
                String digit = ((Button) v).getText().toString();
                if (!operatorSet) {
                    operandA += digit;
                    tvDisplay.setText(operandA);
                } else {
                    operandB += digit;
                    tvDisplay.setText(operandB);
                }
            });
        }

        // Operator buttons
        findViewById(R.id.btnAdd).setOnClickListener(v -> setOperator("+"));
        findViewById(R.id.btnSub).setOnClickListener(v -> setOperator("-"));
        findViewById(R.id.btnMul).setOnClickListener(v -> setOperator("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> setOperator("/"));

        // Equals
        findViewById(R.id.btnEquals).setOnClickListener(v -> calculate());

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> clearAll());

        // Custom Operator — multiplies display value by 5.59 (Student ID: xxx559)
        findViewById(R.id.btnCustom).setOnClickListener(v -> applyCustomOperator());

        // ← ADD THIS: Restore state after rotation
        if (savedInstanceState != null) {
            operandA    = savedInstanceState.getString("operanda", "");
            operandB    = savedInstanceState.getString("operandB", "");
            operator    = savedInstanceState.getString("operator", "");
            operatorSet = savedInstanceState.getBoolean("operatorSet", false);
            tvDisplay.setText(savedInstanceState.getString("display", "0"));
        }
    }

    private void setOperator(String op) {
        if (operandA.isEmpty()) return;
        operator = op;
        operatorSet = true;
    }

    @SuppressLint("SetTextI18n")
    private void calculate() {
        if (operandA.isEmpty() || operandB.isEmpty() || operator.isEmpty()) return;

        double a = Double.parseDouble(operandA);
        double b = Double.parseDouble(operandB);
        double result;

        switch (operator) {
            case "+": result = a + b; break;
            case "-": result = a - b; break;
            case "*": result = a * b; break;
            case "/":
                if (b == 0) {
                    tvDisplay.setText("Cannot divide by zero");
                    clearState();
                    return;
                }
                result = a / b;
                break;
            default: return;
        }

        if (result == (long) result) {
            tvDisplay.setText(String.valueOf((long) result));
        } else {
            tvDisplay.setText(String.valueOf(result));
        }

        operandA = tvDisplay.getText().toString();
        operandB = "";
        operator = "";
        operatorSet = false;
    }

    @SuppressLint("SetTextI18n")
    private void applyCustomOperator() {
        String current = tvDisplay.getText().toString();
        try {
            double value = Double.parseDouble(current);
            double result = value * 5.59;
            if (result == (long) result) {
                tvDisplay.setText(String.valueOf((long) result));
            } else {
                tvDisplay.setText(String.valueOf(result));
            }
            operandA = tvDisplay.getText().toString();
            operandB = "";
            operator = "";
            operatorSet = false;
        } catch (NumberFormatException e) {
            tvDisplay.setText("Error");
        }
    }

    private void clearAll() {
        operandA = "";
        operandB = "";
        operator = "";
        operatorSet = false;
        tvDisplay.setText("0");
    }

    private void clearState() {
        operandA = "";
        operandB = "";
        operator = "";
        operatorSet = false;
    }

    // ← ADD THIS: Save state before rotation
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("operanda", operandA);
        outState.putString("operandB", operandB);
        outState.putString("operator", operator);
        outState.putBoolean("operatorSet", operatorSet);
        outState.putString("display", tvDisplay.getText().toString());
    }
}