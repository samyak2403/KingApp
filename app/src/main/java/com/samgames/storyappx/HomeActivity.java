package com.samgames.storyappx;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.samgames.storyappx.game.ComingSoonActivity;

import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import de.blinkt.openvpn.VpnProfile;

import java.security.SecureRandom;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Random;
import java.util.Locale;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;

public class HomeActivity extends AppCompatActivity {
    // Replace with your actual Unity Game ID
    private static final String UNITY_GAME_ID = "5784278";

    // Replace with your actual Ad Unit IDs
    private static final String INTERSTITIAL_AD_UNIT_ID = "Interstitial_Android";
    private static final String REWARDED_AD_UNIT_ID = "Rewarded_Android";

    private Button showInterstitialAdsBtn;
    private Button showRewardedAdsBtn;

    private Switch btnVpn;
    private TextView timerTextView;
    private boolean interstitialAdLoaded = false;
    private boolean rewardedAdLoaded = false;

    private ProgressDialog progressDialog;

    private ExecutorService executorService;

    private static final int VPN_REQUEST_CODE = 1001;
    private VpnProfile vpnProfile;
    private CountDownTimer countDownTimer;
    private String currentMacAddress; // Store the generated MAC address

    private int activeUsers; // Store the generated active users count

    private void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    private void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @SuppressLint("DefaultLocale")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Generate and store a unique MAC-like identifier when the app starts
        updateBluetoothAddress();

        // Initialize Unity Ads
        initializeUnityAds();

        // Find buttons
        showInterstitialAdsBtn = findViewById(R.id.showInterstitialAdsBtn);
        showRewardedAdsBtn = findViewById(R.id.showRewardedAdsBtn);

        // Find battery percentage TextView
        TextView batteryPercentageTextView = findViewById(R.id.batteryPercentageTextView);

        // Generate and set random battery percentage
        int batteryPercentage = generateRandomBatteryPercentage();
        batteryPercentageTextView.setText(String.format(Locale.US, "Battery: %d%%", batteryPercentage));

        // Initialize executor service for background tasks
        executorService = Executors.newSingleThreadExecutor();

        // Set click listeners
        showInterstitialAdsBtn.setOnClickListener(v -> showInterstitialAd());
        showRewardedAdsBtn.setOnClickListener(v -> showRewardedAd());

        // Example of calculating remaining time
        long exampleInterval = 5000; // 5 seconds in milliseconds
        long remainingTime = calculateRemainingTime(exampleInterval);
        android.util.Log.d("HomeActivity", String.format(Locale.US, "Remaining time: %d seconds", remainingTime));

        timerTextView = findViewById(R.id.timer);
        startCountdownTimer();

        // Automatic update check on app start
        startUpdatingData();

        // Find dataUpdate TextView
        TextView dataUpdateTextView = findViewById(R.id.dataUpdate);
        dataUpdateTextView.setOnClickListener(v ->
                sendNotification("Data Update", "Your virtual data has been updated!")
        );
    }

    private void startCountdownTimer() {
        countDownTimer = new CountDownTimer(15000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                timerTextView.setText(String.format(Locale.US, "%02d:%02d", seconds / 60, seconds % 60));
            }

            @Override
            public void onFinish() {
                timerTextView.setText("00:00");
                // Add any action you want to perform when timer finishes
            }
        }.start();
    }


    private int generateRandomBatteryPercentage() {
        Random random = new Random();
        return random.nextInt(101);
    }

    private int generateRandomWidth() {
        Random random = new Random();
        return random.nextInt(1081) + 800; // Random width between 800px and 1880px
    }

    private int generateRandomHeight() {
        Random random = new Random();
        return random.nextInt(1921) + 600; // Random height between 600px and 2520px
    }

    private GradientDrawable createBackgroundRing() {
        int width = generateRandomWidth();
        int height = generateRandomHeight();

        GradientDrawable ringDrawable = new GradientDrawable();
        ringDrawable.setShape(GradientDrawable.RECTANGLE);
        ringDrawable.setSize(width, height);

        // Set a random color for the ring
        int[] colors = {
                Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,
                Color.MAGENTA, Color.CYAN, Color.rgb(255, 165, 0) // Orange
        };
        int randomColor = colors[new Random().nextInt(colors.length)];

        ringDrawable.setStroke(10, randomColor); // 10px stroke width
        ringDrawable.setColor(Color.TRANSPARENT); // Transparent fill

        return ringDrawable;
    }

    private void initializeUnityAds() {
        UnityAds.initialize(this, UNITY_GAME_ID, false, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Unity Ads initialized successfully", Toast.LENGTH_SHORT).show();
                    loadInterstitialAd();
                    loadRewardedAd();
                });
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                runOnUiThread(() -> {
                    Toast.makeText(HomeActivity.this, "Unity Ads initialization failed: " + message, Toast.LENGTH_LONG).show();
                    Log.e("UnityAds", "Initialization failed: " + error + " - " + message);
                });
            }
        });
    }

    private void loadInterstitialAd() {
        if (!UnityAds.isInitialized()) {
            Log.w("UnityAds", "Cannot load ad: Unity Ads not initialized");
            return;
        }

        UnityAds.load(INTERSTITIAL_AD_UNIT_ID, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                interstitialAdLoaded = true;
                runOnUiThread(() -> {
                    showInterstitialAdsBtn.setEnabled(true);
                    Toast.makeText(HomeActivity.this, "Interstitial Ad Ready", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                interstitialAdLoaded = false;
                runOnUiThread(() -> {
                    showInterstitialAdsBtn.setEnabled(false);
                    Toast.makeText(HomeActivity.this, "Interstitial Ad Load Failed: " + message, Toast.LENGTH_SHORT).show();
                    Log.e("UnityAds", "Interstitial Ad Load Failed: " + error + " - " + message);
                });
            }
        });
    }

    private void loadRewardedAd() {
        if (!UnityAds.isInitialized()) {
            Log.w("UnityAds", "Cannot load ad: Unity Ads not initialized");
            return;
        }

        UnityAds.load(REWARDED_AD_UNIT_ID, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                rewardedAdLoaded = true;
                runOnUiThread(() -> {
                    showRewardedAdsBtn.setEnabled(true);
                    Toast.makeText(HomeActivity.this, "Rewarded Ad Ready", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                rewardedAdLoaded = false;
                runOnUiThread(() -> {
                    showRewardedAdsBtn.setEnabled(false);
                    Toast.makeText(HomeActivity.this, "Rewarded Ad Load Failed: " + message, Toast.LENGTH_SHORT).show();
                    Log.e("UnityAds", "Rewarded Ad Load Failed: " + error + " - " + message);
                });
            }
        });
    }

    private void showInterstitialAd() {
        if (!interstitialAdLoaded) {
            Toast.makeText(this, "Interstitial Ad not loaded yet", Toast.LENGTH_SHORT).show();
            loadInterstitialAd();
            return;
        }

        UnityAds.show(this, INTERSTITIAL_AD_UNIT_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                Toast.makeText(HomeActivity.this, "Interstitial Ad Show Failed: " + message, Toast.LENGTH_SHORT).show();
                Log.e("UnityAds", "Interstitial Ad Show Failed: " + error + " - " + message);
                loadInterstitialAd(); // Retry loading
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                Log.d("UnityAds", "Interstitial Ad Show Started");
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                Log.d("UnityAds", "Interstitial Ad Clicked");
            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                loadInterstitialAd(); // Preload next ad
                Log.d("UnityAds", "Interstitial Ad Show Completed: " + state);
                
                // Open PlanetActivity
                Intent intent = new Intent(HomeActivity.this, com.samgames.storyappx.PlanetActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showRewardedAd() {
        if (!rewardedAdLoaded) {
            Toast.makeText(this, "Rewarded Ad not loaded yet", Toast.LENGTH_SHORT).show();
            loadRewardedAd();
            return;
        }

        UnityAds.show(this, REWARDED_AD_UNIT_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                Toast.makeText(HomeActivity.this, "Rewarded Ad Show Failed: " + message, Toast.LENGTH_SHORT).show();
                Log.e("UnityAds", "Rewarded Ad Show Failed: " + error + " - " + message);
                loadRewardedAd(); // Retry loading
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                Log.d("UnityAds", "Rewarded Ad Show Started");
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                Log.d("UnityAds", "Rewarded Ad Clicked");
            }

            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                loadRewardedAd(); // Preload next ad
                if (state == UnityAds.UnityAdsShowCompletionState.COMPLETED) {
                    // Reward the user
                    runOnUiThread(() -> {
                        Toast.makeText(HomeActivity.this, "Reward Earned!", Toast.LENGTH_SHORT).show();
                        // Open ComingSoonActivity
                        Intent intent = new Intent(HomeActivity.this, ComingSoonActivity.class);
                        startActivity(intent);
                    });
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(HomeActivity.this, "Ad not completed", Toast.LENGTH_SHORT).show();
                    });
                }
                Log.d("UnityAds", "Rewarded Ad Show Completed: " + state);
            }
        });
    }

    private long calculateRemainingTime(long interval) {
        return interval / 1000; // Convert milliseconds to seconds
    }

    private Handler handler = new Handler(Looper.getMainLooper());
    private Random random = new Random();
    private long interval = 5000; // 5 seconds interval, adjust as needed

    private void startUpdatingData() {
        sendNotification("Task Started", "Your System data has started updating.");
        Runnable updateTask = new Runnable() {
            @Override
            public void run() {
                try {
                    updateVirtualData(); // Randomly update data each time the timer triggers
//                    sendNotification("Data Update", "Your virtual data has been updated!");
                    // Add null checks for these methods
                    if (handler != null) {
                        DataUpdate();
                        showRemainingTime();
                        handler.postDelayed(this, interval); // Repeat after the interval
                    } else {
                        android.util.Log.e("HomeActivity", "Handler is null, cannot update data");
                    }
                } catch (Exception e) {
                    android.util.Log.e("HomeActivity", "Error updating data", e);
                    sendNotification("Update Error", "Failed to update system data: " + e.getMessage());
                }
            }
        };

        // Add null check before posting
        if (handler != null) {
            handler.post(updateTask);
        } else {
            android.util.Log.e("HomeActivity", "Cannot start updating data: Handler is null");
        }
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
        SecureRandom random = new SecureRandom();
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

        DataValueChanged("MAC Address", String.format(Locale.US, "MAC Address: %s", macAddress.toString()));
    }

    private void updateBluetoothAddress() {
        SecureRandom random = new SecureRandom();
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

        DataValueChanged("Bluetooth Address", String.format(Locale.US, "Bluetooth Address: %s", bluetoothAddress.toString()));
    }

    private void updateActiveUser() {
        int activeUsers = random.nextInt(5000) + 100;
        DataValueChanged("Active Users", String.format(Locale.US, "Active Users: %d", activeUsers));
    }

    private void updateUserEngagement() {
        int userEngagement = random.nextInt(100);
        DataValueChanged("User Engagement", String.format(Locale.US, "User Engagement: %d%%", userEngagement));
    }

    private void sendNotification(String title, String message) {
        Toast.makeText(this, String.format(Locale.US, "%s: %s", title, message), Toast.LENGTH_SHORT).show();

        // Update dataUpdate TextView text
        TextView dataUpdateTextView = findViewById(R.id.dataUpdate);
        dataUpdateTextView.setText(message);
    }

    private void DataUpdate() {
        android.util.Log.d("HomeActivity", "Performing data update");
        // Add your data update logic here
    }

    private void showRemainingTime() {
        android.util.Log.d("HomeActivity", "Showing remaining time");
        // Add your remaining time display logic here
    }

    private void DataValueChanged(String category, String data) {
        android.util.Log.d("HomeActivity", String.format(Locale.US, "%s: %s", category, data));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}