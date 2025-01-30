package com.samgames.storyappx;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import java.util.Locale;
import java.util.Random;

public class PlanetFullDetailsActivity extends AppCompatActivity {

    private TextView planetNameTextView;
    private TextView planetDescriptionTextView;
    private TextView planetDetailsTextView;
    private TextView planetFactsTextView;
    private LottieAnimationView planetLottieAnimationView;
    private ImageView planetImageView;

    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planet_full_details);

        // Initialize views
        planetNameTextView = findViewById(R.id.planetNameTextView);
        planetDescriptionTextView = findViewById(R.id.planetDescriptionTextView);
        planetDetailsTextView = findViewById(R.id.planetDetailsTextView);
        planetFactsTextView = findViewById(R.id.planetFactsTextView);
        planetLottieAnimationView = findViewById(R.id.planetLottieAnimationView);
        planetImageView = findViewById(R.id.planetImageView);

        // Retrieve planet data from intent
        String planetName = getIntent().getStringExtra("PLANET_NAME");
        String planetDescription = getIntent().getStringExtra("PLANET_DESCRIPTION");
        String planetDetails = getIntent().getStringExtra("PLANET_DETAILS");
        String planetFacts = getIntent().getStringExtra("PLANET_FACTS");
        int lottieAnimationResId = getIntent().getIntExtra("PLANET_ANIMATION", 0);
        int planetImageResId = getIntent().getIntExtra("PLANET_IMAGE", 0);

        // Set planet information
        planetNameTextView.setText(planetName);
        planetDescriptionTextView.setText(planetDescription);
        planetDetailsTextView.setText(planetDetails);
        planetFactsTextView.setText(planetFacts);

        if (lottieAnimationResId != 0) {
            planetLottieAnimationView.setAnimation(lottieAnimationResId);
            planetLottieAnimationView.playAnimation();
        }

        if (planetImageResId != 0) {
            planetImageView.setImageResource(planetImageResId);
        }

        updateVirtualData();
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
        Log.d("PlanetFullDetailsActivity", category + ": " + data);
    }
}