package com.example.polusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.net.Socket;
import java.util.Date;
import java.text.SimpleDateFormat;

import utils.TCPconnection;
import utils.Validation;

public class MainActivity extends AppCompatActivity {

    Button buttonConnect = null;
    Button buttonDisconnect = null;

    TextView info = null;
    TextView ipAddress = null;
    TextView port = null;
    TextView timeOut = null;

    String ipAddressString = "";
    String portString = "";
    String timeOutString = "";

    TCPconnection connection;

    Validation validate;

    Socket socket;

    SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss : ");

    private void onClickConnect(String ipAddress, int port, int timeOut) {
        connection = new TCPconnection(ipAddress, port, timeOut);
        try {
            connection.connect();
            info.append(time.format(new Date()) + " - Подключение установлено.\n");
        } catch (Exception e) {
            info.append(time.format(new Date()) + " - Подключение не удалось.\n");
        }
    }

    private void onClickDisconnect() {
        try {
            connection.disconnect();
            info.append(time.format(new Date()) + " - Подключение разорвано пользователем.\n");
        } catch (Exception e) {
            info.append(time.format(new Date()) + " - Подключение не удалось разорвать или подключение не было установлено.\n");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonConnect = findViewById(R.id.buttonConnect);
        buttonDisconnect = findViewById(R.id.buttonDisconnect);

        info = findViewById(R.id.textInfo);
        ipAddress = findViewById(R.id.editTextIP);
        port = findViewById(R.id.editTextPort);
        timeOut = findViewById(R.id.editTextTimeOut);

        ipAddressString = ipAddress.getText().toString();
        portString = port.getText().toString();
        timeOutString = timeOut.getText().toString();

        info.setMovementMethod(new ScrollingMovementMethod());

        validate = new Validation();

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
                if (validate.isIP4validate(ipAddressString)) {
                    info.append(time.format(new Date()) + " - Введенный IP адрес " + ipAddressString + " корректный.\n");
                    if (validate.isPortValidate(portString)) {
                        info.append(time.format(new Date()) + " - Введенный порт " + portString + " корректный.\n");
                        if (validate.isTimeoutValidate(timeOutString)) {
                            info.append(time.format(new Date()) + " - Введенный таймаут " + timeOutString + " корректный.\n");
                            onClickConnect(ipAddressString, Integer.parseInt(portString), Integer.parseInt(timeOutString));
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
                onClickDisconnect();
            }
        });

    }

}