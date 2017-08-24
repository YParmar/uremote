package com.example.rk.uremote;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.UUID;

public class TVRemoteActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "bluetooth1";

    private FloatingActionButton powerButton;
    private boolean isOn;

    private OutputStream outputStream = null;
    private InputStream inputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote);

        powerButton = (FloatingActionButton) findViewById(R.id.power_button);
        powerButton.setOnClickListener(this);

        try {
            initBluetooth();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void initBluetooth() throws Exception {

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bluetoothAdapter != null){
            if(bluetoothAdapter.isEnabled()){
                Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();

                if(bondedDevices.size() > 0){
                    Object [] devices = bondedDevices.toArray();
                    BluetoothDevice bluetoothDevice = (BluetoothDevice) devices[1];
                    ParcelUuid [] uuids = bluetoothDevice.getUuids();
                    BluetoothSocket socket = bluetoothDevice.createRfcommSocketToServiceRecord(uuids[0].getUuid());
                    socket.connect();
                    outputStream = socket.getOutputStream();
                    inputStream = socket.getInputStream();
                }else {
                    showToast("no Paired devices");
                }

            }else {
                showToast("Enable bluetooth");
            }

        }

    }


    private void sendMessage(String data) throws Exception{
        outputStream.write(data.getBytes());

    }

    public void run(){

        final int BUFFER_SIZE = 1024;
        byte [] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;

        while(true){

            try{

                bytes = inputStream.read(buffer,bytes,BUFFER_SIZE-bytes);
            }catch(Exception e){

                e.printStackTrace();
            }
        }
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.power_button:{
                try{
                    String dataMessage = (isOn) ? "0" : "1";
                    sendMessage(dataMessage);
                    isOn = !isOn;
                    showToast(dataMessage);
                }catch (Exception e){e.printStackTrace();}
            }
            break;


        }
    }
}

