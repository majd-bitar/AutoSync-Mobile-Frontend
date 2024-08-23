package com.example.obdii_reader;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.majd.obd.reader.application.ObdPreferences;
import com.majd.obd.reader.obdCommand.ObdConfiguration;
import com.majd.obd.reader.service.ObdReaderService;
import com.majd.obd.reader.trip.TripRecord;

import static com.majd.obd.reader.constants.DefineObdReader.ACTION_OBD_CONNECTION_STATUS;
import static com.majd.obd.reader.constants.DefineObdReader.ACTION_READ_OBD_REAL_TIME_DATA;

public class MainActivity extends AppCompatActivity {
    private TextView mObdInfoTextView;
    private static final int REQUEST_BLUETOOTH_CONNECT_PERMISSION = 1;

    private Context context;
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mObdInfoTextView = findViewById(R.id.tv_obd_info);
        context=getApplicationContext();

        // Configure OBD commands
        ObdConfiguration.setmObdCommands(this, null);

        // Set gas price per litre
        float gasPrice = 7;

        // Register receiver with actions related to OBD connection status
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_READ_OBD_REAL_TIME_DATA);
        intentFilter.addAction(ACTION_OBD_CONNECTION_STATUS);
        // Registering the receiver
        //registerReceiver(mObdReaderReceiver, intentFilter);
        context.registerReceiver(mObdReaderReceiver, intentFilter, RECEIVER_EXPORTED);
        // Check and request Bluetooth permission
        checkBluetoothPermissionAndStartService();
    }

    private void checkBluetoothPermissionAndStartService() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED) {
            // Request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    REQUEST_BLUETOOTH_CONNECT_PERMISSION);
        } else {
            // Permission is granted, start the service
            startObdReaderService();
        }
    }

    private void startObdReaderService() {
        Intent serviceIntent = new Intent(MainActivity.this, ObdReaderService.class);
        if (startService(serviceIntent) != null) {
            Toast.makeText(this, "OBD Reader Service started successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to start OBD Reader Service", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_BLUETOOTH_CONNECT_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, start the service
                startObdReaderService();
            } else {
                Toast.makeText(this, "Bluetooth permission required to connect to devices", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Broadcast Receiver to receive OBD connection status and real-time data
     */
    private final BroadcastReceiver mObdReaderReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            findViewById(R.id.progress_bar).setVisibility(View.GONE);
            mObdInfoTextView.setVisibility(View.VISIBLE);
            String action = intent.getAction();

            if (ACTION_OBD_CONNECTION_STATUS.equals(action)) {
                String connectionStatusMsg = intent.getStringExtra(ObdReaderService.INTENT_OBD_EXTRA_DATA);
                mObdInfoTextView.setText(connectionStatusMsg);
                Toast.makeText(MainActivity.this, connectionStatusMsg, Toast.LENGTH_SHORT).show();

                if (getString(com.majd.obd.reader.R.string.obd_connected).equals(connectionStatusMsg)) {
                    // OBD connected
                } else if (getString(com.majd.obd.reader.R.string.connect_lost).equals(connectionStatusMsg)) {
                    // OBD disconnected
                } else {
                    // Handle other statuses
                }

            } else if (ACTION_READ_OBD_REAL_TIME_DATA.equals(action)) {
                TripRecord tripRecord = TripRecord.getTripRecode(MainActivity.this);
                mObdInfoTextView.setText(tripRecord.toString());
                Toast.makeText(MainActivity.this, tripRecord.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mObdReaderReceiver);
        stopService(new Intent(this, ObdReaderService.class));
        ObdPreferences.get(this).setServiceRunningStatus(false);
    }
}
