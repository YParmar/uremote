package com.example.rk.uremote;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RK on 8/26/2017.
 */

public class PairListAdapter extends ArrayAdapter<BluetoothDevice>{

    private ArrayList<BluetoothDevice> deviceList;
    private LayoutInflater mLayoutInflater;
    int mViewResourceId ;

    public PairListAdapter(Context context, int tvResourceId, ArrayList<BluetoothDevice> devices){
        super(context, tvResourceId,devices);
        this.deviceList = devices;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = tvResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mLayoutInflater.inflate(mViewResourceId, null);

        BluetoothDevice device = deviceList.get(position);

        if (device != null) {
            TextView deviceName = (TextView) convertView.findViewById(R.id.device_name);
            TextView deviceAdress = (TextView) convertView.findViewById(R.id.device_address);

            if (deviceName != null) {
                deviceName.setText(device.getName());
            }
            if (deviceAdress != null) {
                deviceAdress.setText(device.getAddress());
            }
        }

        return convertView;
    }
}
