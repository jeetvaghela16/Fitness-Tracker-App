package com.example.fitnesstracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor stepSensor;
    private boolean isSensorPresent = false;

    private TextView stepsView, distanceView, caloriesView;
    private Button resetButton;

    private int initialStepCount = -1;  // Use -1 to know if initial value is set
    private int stepOffset = 0;  // To track resets

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepsView = findViewById(R.id.stepsView);
        distanceView = findViewById(R.id.distanceView);
        caloriesView = findViewById(R.id.caloriesView);
        resetButton = findViewById(R.id.resetButton);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        if (sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) != null) {
            stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
            isSensorPresent = true;
        } else {
            Toast.makeText(this, "Step Counter Sensor not available!", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (checkSelfPermission(Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 1001);
            }
        }

        resetButton.setOnClickListener(v -> {
            // When reset button clicked, set the offset to current steps
            if (initialStepCount != -1) {
                stepOffset = (int) (currentSensorValue - initialStepCount);
                updateUI(0); // Reset UI to zero after reset
            }
        });
    }

    private float currentSensorValue = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if (isSensorPresent) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorPresent) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        currentSensorValue = event.values[0];

        if (initialStepCount == -1) {
            initialStepCount = (int) currentSensorValue;
        }

        int stepsSinceStart = (int) (currentSensorValue - initialStepCount);
        int currentSteps = stepsSinceStart - stepOffset;

        if (currentSteps < 0) currentSteps = 0; // prevent negative values

        updateUI(currentSteps);
    }

    private void updateUI(int steps) {
        float distance = steps * 0.78f; // meters
        float calories = steps * 0.04f; // kcal

        stepsView.setText("Steps: " + steps);
        distanceView.setText(String.format("Distance: %.2f meters", distance));
        caloriesView.setText(String.format("Calories: %.2f kcal", calories));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not needed
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission denied. App won't work properly.", Toast.LENGTH_SHORT).show();
                // Optionally close app or disable step tracking
            }
        }
    }
}
