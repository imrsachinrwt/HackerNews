package com.example.sachin.hackernews;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ParsingJson.OnJsonDataAvailable,View.OnClickListener {
    private static final String TAG = "MainActivity";
    private EditText search;
    private Button btnSearch;
    private ListView feedList;
    private String searchTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: in");
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        search=findViewById(R.id.search);
        btnSearch=findViewById(R.id.btnSearch);
        feedList=findViewById(R.id.feedList);
        search.setText("");

        btnSearch.setOnClickListener(this);
        Log.d(TAG, "onCreate: put");
            }
    @Override
    public void onClick(View v) {
        searchTag=search.getText().toString();
        ParsingJson parsingJson=new ParsingJson(this);
        parsingJson.execute(searchTag);
        search.setText("");
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(search.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }
    @Override
    public void onJsonDataAvailable(List<HackerFeed> feed, DownloadStatus status) {
        Log.d(TAG, "onJsonDataAvailable: list is"+feed);

        MyAdapter adapter = new MyAdapter(this, R.layout.hackerdata,feed);
        feedList.setAdapter(adapter);
        Log.d(TAG, "onJsonDataAvailable: out");
    }
}
