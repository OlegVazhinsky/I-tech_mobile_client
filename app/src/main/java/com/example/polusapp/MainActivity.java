package com.example.polusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Date;
import java.text.SimpleDateFormat;

import utils.TCPconnection;
import utils.Validation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss : ");

        TextView info = findViewById(R.id.textInfo);
        TextView ipAddress = findViewById(R.id.editTextIP);
        String ipAggressString = ipAddress.getText().toString();
        TextView port = findViewById(R.id.editTextPort);
        String portString = port.getText().toString();
        TextView timeOut = findViewById(R.id.editTextTimeOut);
        String timeOutString = timeOut.getText().toString();

        info.setMovementMethod(new ScrollingMovementMethod());
        Button buttonConnect = findViewById(R.id.buttonConnect);
        Button buttonDisconnect = findViewById(R.id.buttonDisconnect);

        Validation validate = new Validation();

        info.append("\n" + time.format(new Date()) + " - Старт программы.\n");

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ip = wm.getDhcpInfo().ipAddress;

        if (ip != 0){
            info.append(time.format(new Date()) + " - IP адрес: " + validate.integerIPToString(ip) + ".\n");
        } else {
            info.append(time.format(new Date()) + " - Не удалось подключиться к сети Wi-fi.\n");
        }

        buttonConnect.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (validate.isIP4validate(ipAggressString)) {
                    info.append(time.format(new Date()) + " - Введенный IP адрес " + ipAggressString + " корректный.\n");
                    if (validate.isPortValidate(portString)) {
                        info.append(time.format(new Date()) + " - Введенный порт " + portString + " корректный.\n");
                        if (validate.isTimeoutValidate(timeOutString)) {
                            info.append(time.format(new Date()) + " - Введенный таймаут " + timeOutString + " корректный.\n");
                            TCPconnection connection = new TCPconnection(ipAggressString, Integer.parseInt(portString), Integer.parseInt(timeOutString));
                            try {
                                info.append(new Date() + " - Пытаюсь подключиться.\n");
                                connection.connect();
                                info.append(new Date() + " - Подключение установлено.\n");
                            } catch (IOException e) {
                                info.append(new Date() + " - Не удалось подключиться.\n");
                            }
                        } else {
                            info.append(time.format(new Date()) + " - Введен некорректный таймаут.\n");
                        }
                    } else {
                        info.append(time.format(new Date()) + " - Введен некорректный порт.\n");
                    }
                } else {
                    info.append(time.format(new Date()) + " - Введен некорректный IP адрес.\n");
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