package com.hcmute.baitap09.bai2;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import com.hcmute.baitap09.R;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class Control extends AppCompatActivity {

    ImageButton btnTb1, btnTb2, btnDis;
    TextView txt1, txtMAC;

    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;

    private boolean isBtConnected = false;
    Set<BluetoothDevice> pairedDevices1;
    String address = null;

    private ProgressDialog progress;
    int flaglamp1;
    int flaglamp2;

    // SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent newint = getIntent();
        address = newint.getStringExtra(SocketConnection.EXTRA_ADDRESS); // Receive the address of the Bluetooth device
        setContentView(R.layout.activity_control);

        // Mapping
        btnTb1 = findViewById(R.id.btnTb1);
        btnTb2 = findViewById(R.id.btnTb2);
        txt1 = findViewById(R.id.textV1);
        txtMAC = findViewById(R.id.textViewMAC);
        btnDis = findViewById(R.id.btnDisc);

        new ConnectBT().execute(); // Call the class to connect

        btnTb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thietTbi1();
            }
        });

        btnTb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thiettbi7();
            }
        });

        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Disconnect();
            }
        });
    }

    // Write function
    private void thietTbi1() {
        if (btSocket != null) {
            try {
                if (this.flaglamp1 == 0) {
                    this.flaglamp1 = 1;
                    this.btnTb1.setBackgroundResource(R.drawable.btnonoff);
                    btSocket.getOutputStream().write("1".toString().getBytes());
                    txt1.setText("Device 1 is on");
                    return;
                } else {
                    if (this.flaglamp1 != 1) return;

                    this.flaglamp1 = 0;
                    this.btnTb1.setBackgroundResource(R.drawable.btnotconnect);
                    btSocket.getOutputStream().write("A".toString().getBytes());
                    txt1.setText("Device 1 is off");
                    return;
                }
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private void Disconnect() {
        if (btSocket != null) { // If the btSocket is busy
            try {
                btSocket.close(); // Close connection
            } catch (IOException e) {
                msg("Error");
            }
        }
        finish(); // Return to the first layout
    }

    private void thiettbi7() {
        if (btSocket != null) {
            try {
                if (this.flaglamp2 == 0) {
                    this.flaglamp2 = 1;
                    this.btnTb2.setBackgroundResource(R.drawable.btnonoff);
                    btSocket.getOutputStream().write("7".toString().getBytes());
                    txt1.setText("Device number 7 is on");
                    return;
                } else {
                    if (this.flaglamp2 != 1) return;

                    this.flaglamp2 = 0;
                    this.btnTb2.setBackgroundResource(R.drawable.btnotconnect);
                    btSocket.getOutputStream().write("G".toString().getBytes());
                    txt1.setText("Device number 7 is off");
                    return;
                }
            } catch (IOException e) {
                msg("Error");
            }
        }
    }

    private class ConnectBT extends AsyncTask<Void, Void, Void> { // UI thread

        private boolean ConnectSuccess = true; // if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            // Show a progress dialog
            progress = ProgressDialog.show(Control.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            // While the progress dialog is shown, the connection is done in background
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter(); // Get the mobile Bluetooth device

                    // Connects to the device's address and checks if it's available
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);

                    if (ActivityCompat.checkSelfPermission(Control.this, Manifest.permission.BLUETOOTH_CONNECT)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Create an RFCOMM (SPP) connection
                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);

                        BluetoothAdapter.getDefaultAdapter().cancelDiscovery();

                        btSocket.connect(); // Start connection
                    }
                }
            } catch (IOException e) {
                ConnectSuccess = false; // If the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // After the doInBackground, it checks if everything went fine
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Connection failed! Check device.");
                finish();
            } else {
                msg("Connection successful.");
                isBtConnected = true;
                pairedDevicesList1();
            }

            progress.dismiss();
        }
    }

    private void pairedDevicesList1() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            pairedDevices1 = myBluetooth.getBondedDevices();

            if (pairedDevices1.size() > 0) {
                for (BluetoothDevice bt : pairedDevices1) {
                    txtMAC.setText(bt.getName() + " " + bt.getAddress()); // Get the device's name and the address
                }
            } else {
                Toast.makeText(getApplicationContext(), "No connected device found.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
