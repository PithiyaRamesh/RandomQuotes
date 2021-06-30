package com.randomquotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

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