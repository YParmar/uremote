package com.example.rk.uremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    public static final String POSITION = "position";
    public static final String TOOLBAR_TITLE = "toolbar_title";
    private Toolbar toolbar;
    private ApplianceAdapter applianceAdapter;

    private int[] applianceImages = {R.drawable.television_red,
            R.drawable.air_conditioner,
            R.drawable.speaker,
            R.drawable.chandelier,
            R.drawable.projector
    };

    private String[] applianceNames = {"Television", "Air Conditioner", "Music System", "Lights", "Projector"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Home");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        applianceAdapter = new ApplianceAdapter(this, applianceNames, applianceImages);
        gridView = (GridView) findViewById(R.id.appliance_view);
        gridView.setAdapter(applianceAdapter);
        gridView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, TVRemoteActivity.class);
        intent.putExtra(POSITION, position);
        intent.putExtra(TOOLBAR_TITLE, applianceNames[position]);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                break;
        }

        return true;
    }
}
