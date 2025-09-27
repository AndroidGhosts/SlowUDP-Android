package com.evozi.slowudp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    
    private EditText etServerIP, etUsername, etPassword, etPort;
    private Button btnInstall, btnStart, btnStop, btnStatus, btnConfig;
    private TextView tvOutput;
    private SlowUDPManager slowUDPManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initializeViews();
        slowUDPManager = new SlowUDPManager(this);
        
        setupClickListeners();
    }
    
    private void initializeViews() {
        etServerIP = findViewById(R.id.etServerIP);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etPort = findViewById(R.id.etPort);
        btnInstall = findViewById(R.id.btnInstall);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnStatus = findViewById(R.id.btnStatus);
        btnConfig = findViewById(R.id.btnConfig);
        tvOutput = findViewById(R.id.tvOutput);
    }
    
    private void setupClickListeners() {
        btnInstall.setOnClickListener(v -> installSlowUDP());
        btnStart.setOnClickListener(v -> startSlowUDP());
        btnStop.setOnClickListener(v -> stopSlowUDP());
        btnStatus.setOnClickListener(v -> checkStatus());
        btnConfig.setOnClickListener(v -> showConfig());
    }
    
    private void installSlowUDP() {
        if (!validateInputs()) return;
        
        String serverIP = etServerIP.getText().toString();
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String port = etPort.getText().toString();
        
        showMessage("جاري التثبيت...");
        
        new Thread(() -> {
            try {
                String result = slowUDPManager.installSlowUDP(serverIP, username, password, port);
                runOnUiThread(() -> {
                    tvOutput.setText(result);
                    showMessage("تم التثبيت بنجاح");
                });
            } catch (Exception e) {
                runOnUiThread(() -> {
                    tvOutput.setText("خطأ: " + e.getMessage());
                    showMessage("فشل التثبيت");
                });
            }
        }).start();
    }
    
    private void startSlowUDP() {
        new Thread(() -> {
            String result = slowUDPManager.startSlowUDP();
            runOnUiThread(() -> {
                tvOutput.setText(result);
                showMessage("تم بدء الخدمة");
            });
        }).start();
    }
    
    private void stopSlowUDP() {
        new Thread(() -> {
            String result = slowUDPManager.stopSlowUDP();
            runOnUiThread(() -> {
                tvOutput.setText(result);
                showMessage("تم إيقاف الخدمة");
            });
        }).start();
    }
    
    private void checkStatus() {
        new Thread(() -> {
            String result = slowUDPManager.checkStatus();
            runOnUiThread(() -> tvOutput.setText(result));
        }).start();
    }
    
    private void showConfig() {
        new Thread(() -> {
            String result = slowUDPManager.getConfig();
            runOnUiThread(() -> tvOutput.setText(result));
        }).start();
    }
    
    private boolean validateInputs() {
        if (etServerIP.getText().toString().isEmpty()) {
            showMessage("يرجى إدخال عنوان الخادم");
            return false;
        }
        if (etUsername.getText().toString().isEmpty()) {
            showMessage("يرجى إدخال اسم المستخدم");
            return false;
        }
        return true;
    }
    
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
                  }
