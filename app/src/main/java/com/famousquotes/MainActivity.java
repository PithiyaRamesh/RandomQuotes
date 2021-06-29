package com.famousquotes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
public static final String TAG = "_MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }
        catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        ArrayList<DataClass> quotes = this.getIntent().getParcelableArrayListExtra("quotes");
        mSetupRecycler(quotes);
    }

    private void mSetupRecycler(ArrayList<DataClass> quotes) {
        if (quotes != null && quotes.size() > 0) {
            RecyclerView recyclerView = findViewById(R.id.rec_main_quotes);
            RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,quotes);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "No quotes to show", Toast.LENGTH_LONG).show();
        }
    }
}