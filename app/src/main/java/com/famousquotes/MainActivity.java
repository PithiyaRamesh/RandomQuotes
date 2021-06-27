package com.famousquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
public static final String TAG = "_MainActivity";
    DataClass[] quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
            String url = "https://type.fit/api/quotes";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            JSONArray jsonArray =new JSONArray(response);
                            JSONObject jsonObject;
                            quotes = new DataClass[jsonArray.length()];
                            for(int i = 0; i<jsonArray.length(); i++) {
                                jsonObject = jsonArray.getJSONObject(i);
                                quotes[i] = new DataClass(jsonObject.getString("text"),"~"+ jsonObject.getString("author"));
                                setContentView(R.layout.activity_main);
                                mSetupRecycler();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "ERROR: "+ e);
                            Toast.makeText(getApplicationContext(),"Please Check Internet: "+e, Toast.LENGTH_LONG).show();
                        }
                    }, error -> Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show());

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void mSetupRecycler() {
        if (quotes != null && quotes.length > 0) {
            RecyclerView recyclerView = findViewById(R.id.rec_main_quotes);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,quotes);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "No quotes to show", Toast.LENGTH_LONG).show();
        }
    }
}