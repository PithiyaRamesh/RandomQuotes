package com.famousquotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "_"+SplashActivity.class.getName();
    ArrayList<DataClass> quotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(),"No Internet Retrying", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> {
                if (!isConnected()) {
                    //TODO: Show favorite quotes activity
                    Toast.makeText(getApplicationContext(),"No Internet", Toast.LENGTH_SHORT).show();
                    finishAffinity();
                }
            }, 3000);
        } else {
            String url = "https://type.fit/api/quotes";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject;
                            quotes = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                quotes.add(new DataClass(jsonObject.getString("text"), "~" + jsonObject.getString("author")));
                            }
                            Intent intentMain = new Intent(this, MainActivity.class);
                            intentMain.putParcelableArrayListExtra("quotes", quotes);
                            startActivity(intentMain);
                        } catch (Exception e) {
                            Log.e(TAG, "ERROR: " + e);
                            Toast.makeText(getApplicationContext(), "Please Check Internet: " + e, Toast.LENGTH_LONG).show();
                        }
                    }, error -> Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show());

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null){
            return false;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            NetworkCapabilities cap = cm.getNetworkCapabilities(cm.getActiveNetwork());
            if (cap == null) {
                return false;
            }
            if(cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                return isInternet();
            }
        } else {
            Network[] networks = cm.getAllNetworks();
            for (Network n: networks) {
                NetworkInfo nInfo = cm.getNetworkInfo(n);
                if (nInfo != null && nInfo.isConnected()){
                    return isInternet();
                }
            }
        }
        return false;
    }

    private boolean isInternet(){
        Process process;
        try {
            process = Runtime.getRuntime().exec("/system/bin/ping -c 1 8.8.8.8");
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (InterruptedException | IOException e) {
            Log.e(TAG,e.toString());
        }
        return false;
    }
}