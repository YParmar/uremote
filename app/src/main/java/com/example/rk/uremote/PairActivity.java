package com.example.rk.uremote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PairActivity extends AppCompatActivity {
private ListView pairList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair);

        pairList = (ListView) findViewById(R.id.pair_list);


    }
}
