package com.example.rk.uremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PairActivity extends AppCompatActivity {
    private ListView pairList;
    private BluetoothDevice connDevice;
    private BluetoothAdapter bluetoothAdapter;
    private ArrayList<BluetoothDevice> bluetoothDevicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        pairList = (ListView) findViewById(R.id.pair_list);

        IntentFilter pairFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(pairReceiver, pairFilter);

        Set<BluetoothDevice> bluetoothDeviceSet = bluetoothAdapter.getBondedDevices();
        bluetoothDevicesList = new ArrayList<>(bluetoothDeviceSet);

        PairListAdapter adapter = new PairListAdapter(this, R.layout.row_paired_device_layout, bluetoothDevicesList);
        pairList.setAdapter(adapter);
        pairList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                connDevice = bluetoothDevicesList.get(position);
                Intent intent = new Intent();
                intent.putExtra("DEVICE", connDevice);
                setResult(100, intent);
                finish();

            }
        });

    }

    BroadcastReceiver pairReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    connDevice = mDevice;
                }
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                }
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        try{
            unregisterReceiver(pairReceiver);
        }catch (Exception e){e.printStackTrace();}
    }
}
