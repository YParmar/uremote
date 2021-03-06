package com.example.rk.uremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.UUID;

public class TVRemoteActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton powerButton;
    private ImageView volumeUp;
    private ImageView volumeDown;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSendService bluetoothSendService;
    private BluetoothDevice mDevice;

    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        powerButton = (FloatingActionButton) findViewById(R.id.power_button);
        volumeUp = (ImageView) findViewById(R.id.volume_up);
        volumeUp.setOnClickListener(this);
        volumeDown = (ImageView) findViewById(R.id.volume_down);
        volumeDown.setOnClickListener(this);
        powerButton.setOnClickListener(this);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.power_button: {
                enableDisableBluetooth();
            }
            break;

            case R.id.volume_up: {
            }
            break;

            case R.id.volume_down: {
                byte [] bytes = "1".getBytes(Charset.defaultCharset());
                bluetoothSendService.write(bytes);
            }
            break;

        }
    }

    private void startConnection() {
        startConnectService(mDevice, MY_UUID_INSECURE);
    }

    private void startConnectService(BluetoothDevice device , UUID uuid){
        bluetoothSendService.startClient(device, uuid);
    }

    private void enableDisableBluetooth() {
        if (bluetoothAdapter == null) {
            showToast("Your device does not have bluetooth capabilities");

        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableIntent);
            IntentFilter BtIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(broadcastReceiver, BtIntent);
        }
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
            IntentFilter BtIntent = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
            registerReceiver(broadcastReceiver, BtIntent);
        }

    }

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(bluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, bluetoothAdapter.ERROR);

                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        showToast("Bluetooth turned off");
                        break;

                    case BluetoothAdapter.STATE_ON:
                        showToast("Bluetooth turned on");
                        Intent pairedIntent = new Intent(TVRemoteActivity.this, PairActivity.class);
                        startActivityForResult(pairedIntent, 100);
                        break;

                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(broadcastReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            if(resultCode == 100){
                mDevice = data.getParcelableExtra("DEVICE");
                bluetoothSendService = new BluetoothSendService(TVRemoteActivity.this);
                startConnection();
            }else {
                showToast("wrong");
            }
        }else {
            showToast("intent null");
        }
    }
}

