package com.example.rk.uremote;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BrandActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView commingSoon;


    private RecyclerView brandRecyclerView;
    private BrandAdapter brandAdapter;
    private int[] brandImages = {
            R.drawable.ic_tv_black,
            R.drawable.ic_tv_black,
            R.drawable.ic_tv_black,
            R.drawable.ic_tv_black
    };

    private String[] brandNames = {
            "SONY", "PHILIPS", "VIDEOCON", "TOSHIBA"
    };

    private String[] acBrandNames = {
            "j mj", "PHIL mjkIPS", " ,kk,k,", ", k,k,k,"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);

        if (getIntent() == null) {
            return;
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getIntent().getStringExtra(MainActivity.TOOLBAR_TITLE));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        commingSoon = (TextView) findViewById(R.id.comming_soon);
        brandRecyclerView = (RecyclerView) findViewById(R.id.brand_recyclerview);
        brandRecyclerView.setVisibility(View.VISIBLE);
        brandRecyclerView.setHasFixedSize(true);
        brandRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        switch (getIntent().getIntExtra(MainActivity.POSITION, -1)) {

            case 0:
                brandAdapter = new BrandAdapter(this, brandImages, brandNames);
                break;
            case 1:
                brandAdapter = new BrandAdapter(this, brandImages, acBrandNames);
                break;

            default:
                brandRecyclerView.setVisibility(View.GONE);
                commingSoon.setVisibility(View.VISIBLE);
                break;
        }


        brandRecyclerView.setAdapter(brandAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case android.R.id.home:
                onBackPressed();
                break;
        }
        return  true;
    }
}
