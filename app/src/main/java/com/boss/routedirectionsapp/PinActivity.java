package com.boss.routedirectionsapp;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.goodiebag.pinview.Pinview;

public class PinActivity extends AppCompatActivity {
    Pinview pin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        pin = (Pinview) findViewById(R.id.pinview);
        pin.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                //Make api calls here or what not
                if (pinview.getValue().equals("1111")) {
                    Toast.makeText(PinActivity.this, "Logged In", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PinActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(PinActivity.this, "Failed Attempt", Toast.LENGTH_SHORT).show();
                    String phoneNumber = "9650498659";
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNumber, null, "HELP ME", null, null);
                    Toast.makeText(PinActivity.this, "Notifying", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
