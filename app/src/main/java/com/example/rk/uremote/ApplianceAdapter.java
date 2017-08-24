package com.example.rk.uremote;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by RK on 8/19/2017.
 */

public class ApplianceAdapter extends BaseAdapter {

    private Context context;
    private String [] applianceNames;
    private String [] applianceColor;
    private int [] applianceImages;

    public ApplianceAdapter(Context context, String[] applianceNames, String [] applianceColor, int [] applianceImages) {
        this.context=context;
        this.applianceNames = applianceNames;
        this.applianceColor = applianceColor;
        this.applianceImages = applianceImages;
    }

    public ApplianceAdapter(Context context, String[] applianceNames,  int [] applianceImages) {
        this.context=context;
        this.applianceNames = applianceNames;
        //this.applianceColor = applianceColor;
        this.applianceImages = applianceImages;
    }

    @Override
    public int getCount() {
        return applianceNames.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridview;
        ImageView gridimg;
        TextView gridtxt;

        if(convertView == null){
            gridview = inflater.inflate(R.layout.grid_layout, null);
            gridimg = (ImageView)gridview.findViewById(R.id.gridimg);
            gridtxt = (TextView)gridview.findViewById(R.id.gridtxt);

            gridimg.setImageResource(applianceImages[position]);
            //gridimg.setColorFilter(Color.parseColor(applianceColor[position]));
            gridtxt.setText(applianceNames[position]);

        }else{

            gridview = convertView;
        }
        return gridview;
    }
}
