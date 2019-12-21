package com.boss.routedirectionsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String IP = "http://10.104.202.6:3344";
    private static final String Safest = "/map_get";
    EditText source, destination;
    Button button;
    ProgressDialog progressDialog;
    String sourceLat = "NaN";
    String sourceLong = "NaN";
    String destinationLat = "NaN";
    String destinationLong = "NaN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        source = findViewById(R.id.source);
        destination = findViewById(R.id.destination);
        button = findViewById(R.id.get);

        source.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateCoordinates();
            }
        });

        destination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateCoordinates();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!source.getText().toString().equals("") && !destination.getText().toString().equals("")) {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setTitle("Fetching Safest Route");
                    progressDialog.setMessage("Please Wait ...");
                    // progressDialog.setCancelable(false);
                    progressDialog.show();


                    OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
                    okhttpBuilder.connectTimeout(10, TimeUnit.MINUTES) // connect timeout
                            .writeTimeout(10, TimeUnit.MINUTES) // write timeout
                            .readTimeout(10, TimeUnit.MINUTES); // read timeout

                    OkHttpClient client = okhttpBuilder.build();

                    // Initialize Builder (not RequestBody)
                    FormBody.Builder builder = new FormBody.Builder();

                    builder.add("start_lat", sourceLat);
                    builder.add("start_long", sourceLong);
                    builder.add("end_lat", destinationLat);
                    builder.add("end_long", destinationLong);

                    // Create RequestBody
                    RequestBody requestBody = builder.build();

                    Request request = new Request.Builder()
                            .url(IP + Safest)
                            //.post(requestBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("ERROR", e.getMessage());
                            call.cancel();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String myResponse = response.body().string();
                            Log.e("RESPONSE", myResponse);
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Enter Location(s)", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateCoordinates() {
        GeocodingLocation locationAddress = new GeocodingLocation();
        if (!source.getText().toString().equals("")) {
            locationAddress.getAddressFromLocation("src", source.getText().toString(), getApplicationContext(), new GeocoderHandler());
        }
        if (!destination.getText().toString().equals("")) {
            locationAddress.getAddressFromLocation("dest", destination.getText().toString(), getApplicationContext(), new GeocoderHandler());
        }

        Log.e("START_LAT", sourceLat);
        Log.e("START_LONG", sourceLong);
        Log.e("END_LAT", destinationLat);
        Log.e("END_LONG", destinationLong);
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress = null;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    if (bundle.getString("sd") != null && bundle.getString("sd").equals("src")) {
                        String lines[] = locationAddress.split("\\r?\\n");
                        sourceLat = lines[0];
                        sourceLong = lines[1];
                    } else if (bundle.getString("sd") != null && bundle.getString("sd").equals("dest")) {
                        String lines[] = locationAddress.split("\\r?\\n");
                        destinationLat = lines[0];
                        destinationLong = lines[1];
                    }
                    break;
                default:
                    locationAddress = null;
            }
        }
    }

}
