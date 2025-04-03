package com.hcmute.baitap09.bai2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hcmute.baitap09.R;

import java.util.ArrayList;
import java.util.Set;

public class SocketConnection extends AppCompatActivity {

    Button btnPaired;
    ListView listDanhSach;
    public static int REQUEST_BLUETOOTH = 1;

    // Bluetooth
    private BluetoothAdapter myBluetooth = null;
    private Set<BluetoothDevice> pairedDevices;

    public static String EXTRA_ADDRESS = "device_address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socketconnection);

        // Mapping
        btnPaired = findViewById(R.id.btnTimthietbi);
        listDanhSach = findViewById(R.id.listTb);

        // Check if the device has Bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        if (myBluetooth == null) {
            // Show a message that the device has no Bluetooth adapter
            Toast.makeText(getApplicationContext(), "Bluetooth device not turned on", Toast.LENGTH_LONG).show();
            finish();
        } else if (!myBluetooth.isEnabled()) {
            // Ask the user to turn the Bluetooth on
            Intent turnBTon = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "Bluetooth device not turned on", Toast.LENGTH_LONG).show();
            }

            Toast.makeText(getApplicationContext(), "Bluetooth device is enabled", Toast.LENGTH_LONG).show();

            startActivityForResult(turnBTon, REQUEST_BLUETOOTH);
        }

        // Perform device search
        btnPaired.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevicesList();
            }
        });
    }

    // Dedicated to him
    private void pairedDevicesList() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            pairedDevices = myBluetooth.getBondedDevices();
            ArrayList<String> list = new ArrayList<>();

            if (pairedDevices.size() > 0) {
                for (BluetoothDevice bt : pairedDevices) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(getApplicationContext(), "List of enabled Bluetooth devices", Toast.LENGTH_LONG).show();
                        list.add(bt.getName() + "\n" + bt.getAddress()); // Get the device's name and the address
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "No paired device found.", Toast.LENGTH_LONG).show();
            }

            final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
            listDanhSach.setAdapter(adapter);
            listDanhSach.setOnItemClickListener(myListClickListener); // Method called when the device from the list is clicked
        }
    }

    // Create adapter
    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Get the device MAC address, the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Make an intent to start next activity
            Intent i = new Intent(SocketConnection.this, Control.class);
            i.putExtra(EXTRA_ADDRESS, address); // this will be received at BlueControl (class) Activity
            startActivity(i);
        }
    };
}
