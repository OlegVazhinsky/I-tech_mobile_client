package com.example.polusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import utils.Validation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView info = findViewById(R.id.textInfo);
        Button buttonConnect = findViewById(R.id.buttonConnect);
        Button buttonDisconnect = findViewById(R.id.buttonDisconnect);

        Validation validate = new Validation();

        info.append("\nСтарт программы.\n");

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ip = wm.getDhcpInfo().ipAddress;

        if (ip != 0){
            info.append("IP адрес: " + validate.integerIPToString(ip) + ".\n");
        } else {
            info.append("Не удалось подключиться к сети Wi-fi.\n");
        }

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validate.isIP4validate(validate.integerIPToString(ip))) {
                    info.append("Введенный IP адрес корректный. Пытаюсь подключиться к " + validate.integerIPToString(ip) + "...\n");
                } else {
                    info.append("Введен некорректный IP адрес.\n");
                }
            }
        });

        buttonDisconnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                info.append("Пытаюсь отключиться.\n");
            }
        });

    }
}