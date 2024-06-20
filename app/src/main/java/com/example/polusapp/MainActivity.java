package com.example.polusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static String integerToStringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                ((ip >> 24) & 0xFF);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TextView textIpAddress = (TextView) findViewById(R.id.textIpAddress);
        TextView ipAddress = (TextView) findViewById(R.id.ipAddress);

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ip = wm.getDhcpInfo().ipAddress;

        if (ip != 0){
            ipAddress.setText("" + integerToStringIP(ip));

        } else {
            ipAddress.setText("Can not connet to Wi-fi network.");
        }



    }
}