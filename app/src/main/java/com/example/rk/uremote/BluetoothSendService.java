package com.example.rk.uremote;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.nfc.Tag;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Created by pramod on 02-09-2017.
 */

public class BluetoothSendService {
    private static final String TAG = "BluetoothService";
    private static final String APP_NAME = "MyApp";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private AcceptThread acceptThread;
    private final BluetoothAdapter bluetoothAdapter;
    private Context context;
    private BluetoothDevice mDevice;
    private UUID deviceUUID;
    private ConnectThread mConnectThread;
    private ConnectedThread mConnectedThread;
    private ProgressDialog progressDialog;

    public BluetoothSendService(Context context) {

        this.context = context;
        this.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket serverSocket;

        public AcceptThread() {

            BluetoothServerSocket temp = null;
            try {
                temp = bluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(APP_NAME, MY_UUID_INSECURE);
                Toast.makeText(context, "Setting up server using" + MY_UUID_INSECURE, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            serverSocket = temp;

        }

        @Override
        public void run() {
            BluetoothSocket bluetoothSocket = null;
            try {
                bluetoothSocket = serverSocket.accept();
                Toast.makeText(context, "Socket connection accepted", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bluetoothSocket != null) {
                connected(bluetoothSocket, mDevice);
            }
        }

        public void cancel() {
            Toast.makeText(context, "Canceling connection...", Toast.LENGTH_SHORT).show();


            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket bluetoothSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {

            mDevice = device;
            deviceUUID = uuid;
        }

        @Override
        public void run() {
            BluetoothSocket temp = null;
            try {
                temp = mDevice.createRfcommSocketToServiceRecord(deviceUUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bluetoothSocket = temp;
            bluetoothAdapter.cancelDiscovery();
            try {
                bluetoothSocket.connect();
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connected(bluetoothSocket, mDevice);

        }

        public void cancel() {
            Toast.makeText(context, "Canceling connection...", Toast.LENGTH_SHORT).show();
            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    public synchronized void start() {
        if (mConnectThread != null) {

            mConnectThread.cancel();
            mConnectThread = null;
        }
        if (acceptThread == null) {

            acceptThread = new AcceptThread();
            acceptThread.start();

        }
    }

    public void startClient(BluetoothDevice device, UUID uuid) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Starting service please wait...");
        progressDialog.show();

        mConnectThread = new ConnectThread(device, uuid);
        mConnectThread.start();
    }

    private class ConnectedThread extends Thread {

        private final BluetoothSocket socket;
        private final InputStream inputStream;
        private final OutputStream outputStream;

        public ConnectedThread(BluetoothSocket socket) {
            this.socket = socket;
            InputStream tempIns = null;
            OutputStream tempOps = null;
            progressDialog.dismiss();

            try {
                tempIns = socket.getInputStream();
                tempOps = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();

            }
            inputStream = tempIns;
            outputStream = tempOps;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;
            while (true) {

                try {
                    bytes = inputStream.read(buffer);
                    String incomingMessage = new String(buffer, 0, bytes);
                    Toast.makeText(context, incomingMessage, Toast.LENGTH_SHORT).show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        public void cancel() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Toast.makeText(context,text, Toast.LENGTH_SHORT).show();

            try {
                outputStream.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connected(BluetoothSocket socket , BluetoothDevice device){
        mConnectedThread = new ConnectedThread(socket);
        mConnectedThread.start();
    }

    public void write(byte [] out){
        mConnectedThread.write(out);
    }



}
