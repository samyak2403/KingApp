package com.samgames.storyappx.game;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import com.samgames.storyappx.HomeActivity;
import com.samgames.storyappx.R;

import java.util.Locale;
import java.util.Random;

public class ComingSoonActivity extends AppCompatActivity {

    private Random random = new Random();
    private TextView timerTextView;
    private Button backButton;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);

        timerTextView = findViewById(R.id.timer);
        backButton = findViewById(R.id.back_button);
        
        // Initially disable the back button
        backButton.setEnabled(false);
        backButton.setBackgroundColor(Color.GRAY);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to HomeActivity
                Intent intent = new Intent(ComingSoonActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close this activity
            }
        });

        startCountdownTimer();

        updateVirtualData();
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(15000, 1000) { 
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerTextView.setText(String.format(Locale.US, "%02d:%02d", seconds / 60, seconds % 60));

                // More attractive color progression for 15 seconds
                if (seconds <= 3) {
                    // Vibrant red for last 3 seconds
                    backButton.setBackgroundColor(Color.parseColor("#FF4136")); // Bright red
                } else if (seconds <= 7) {
                    // Vibrant orange for 7-3 seconds
                    backButton.setBackgroundColor(Color.parseColor("#FF851B")); // Bright orange
                } else if (seconds <= 15) {
                    // Vibrant green for 15-7 seconds
                    backButton.setBackgroundColor(Color.parseColor("#2ECC40")); // Bright green
                }
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                // Enable the back button and set to a gradient or vibrant color
                backButton.setEnabled(true);
                backButton.setBackgroundColor(Color.parseColor("#9C27B0")); // Vibrant purple
            }
        }.start();
    }

    private void updateVirtualData() {
        updateBuildInfo();
        updateTelephonyInfo();
        updateBatteryLevel();
        updateNetworkInfo();
        updateDisplayInfo();
        updateMacAddress();
        updateBluetoothAddress();
        updateActiveUser();
        updateUserEngagement();
    }

    private void updateBuildInfo() {
        String model = "Model_" + random.nextInt(100);
        String brand = "Brand_" + random.nextInt(50);
        String manufacturer = "Manufacturer_" + random.nextInt(30);
        String releaseVersion = "Release_" + random.nextInt(10);
        String sdkVersion = "SDK_" + random.nextInt(100);
        
        DataValueChanged("Build Info", String.format(Locale.US, 
            "Model:%s, Brand:%s, Manufacturer:%s, Release:%s, SDK:%s", 
            model, brand, manufacturer, releaseVersion, sdkVersion
        ));
    }

    private void updateTelephonyInfo() {
        String deviceId = "DEV" + random.nextInt(1000000);
        String subscriberId = "Sub" + random.nextInt(1000000);
        String simSerialNumber = "SIM" + random.nextInt(5000);
        
        DataValueChanged("Telephony Info", String.format(Locale.US, 
            "Device ID:%s, Subscriber ID:%s, SIM Serial:%s", 
            deviceId, subscriberId, simSerialNumber
        ));
    }

    private void updateBatteryLevel() {
        int batteryLevel = random.nextInt(101);
        DataValueChanged("Battery Level", String.format(Locale.US, "Battery Level: %d%%", batteryLevel));
    }

    private void updateNetworkInfo() {
        String networkType = "Network_" + random.nextInt(10);
        String networkOperator = "Operator_" + random.nextInt(20);
        DataValueChanged("Network Info", String.format(Locale.US, 
            "Network Type:%s, Network Operator:%s", 
            networkType, networkOperator
        ));
    }

    private void updateDisplayInfo() {
        int screenWidth = random.nextInt(1080) + 800;
        int screenHeight = random.nextInt(1920) + 600;
        DataValueChanged("Display Info", String.format(Locale.US, 
            "Screen Width:%d, Screen Height:%d", screenWidth, screenHeight
        ));
    }

    private void updateMacAddress() {
        byte[] macAddr = new byte[6];
        random.nextBytes(macAddr);
        
        // Ensure the first byte is not a multicast address
        macAddr[0] = (byte) (macAddr[0] & (byte) 0xfe);
        
        // Convert to a formatted MAC address string
        StringBuilder macAddress = new StringBuilder();
        for (int i = 0; i < macAddr.length; i++) {
            macAddress.append(String.format(Locale.US, "%02X%s", macAddr[i], 
                (i < macAddr.length - 1) ? ":" : ""));
        }
        
        DataValueChanged("MAC Address", "MAC Address: " + macAddress.toString());
    }

    private void updateBluetoothAddress() {
        byte[] macAddr = new byte[6];
        random.nextBytes(macAddr);
        
        // Ensure the first byte is not a multicast address
        macAddr[0] = (byte) (macAddr[0] & (byte) 0xfe);
        
        // Convert to a formatted Bluetooth MAC address string
        StringBuilder bluetoothAddress = new StringBuilder();
        for (int i = 0; i < macAddr.length; i++) {
            bluetoothAddress.append(String.format(Locale.US, "%02X%s", macAddr[i], 
                (i < macAddr.length - 1) ? ":" : ""));
        }
        
        DataValueChanged("Bluetooth Address", "Bluetooth Address: " + bluetoothAddress.toString());
    }

    private void updateActiveUser() {
        int activeUsers = random.nextInt(5000) + 100;
        DataValueChanged("Active Users", String.format(Locale.US, "Active Users: %d", activeUsers));
    }

    private void updateUserEngagement() {
        int userEngagement = random.nextInt(100);
        DataValueChanged("User Engagement", String.format(Locale.US, "User Engagement: %d%%", userEngagement));
    }

    private void DataValueChanged(String category, String data) {
        Log.d("ComingSoonActivity", category + ": " + data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}