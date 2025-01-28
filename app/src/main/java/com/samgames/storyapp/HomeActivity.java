package com.samgames.storyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.samgames.storyapp.game.ComingSoonActivity;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.unity3d.ads.IUnityAdsInitializationListener;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {
    // Replace with your actual Unity Game ID
    private static final String UNITY_GAME_ID = "5784278";

    // Replace with your actual Ad Unit IDs
    private static final String INTERSTITIAL_AD_UNIT_ID = "Interstitial_Android";
    private static final String REWARDED_AD_UNIT_ID = "Rewarded_Android";

    private Button showInterstitialAdsBtn;
    private Button showRewardedAdsBtn;
    private Button checkUpdateButton;

    private boolean interstitialAdLoaded = false;
    private boolean rewardedAdLoaded = false;

    private ProgressDialog progressDialog;

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

        // Initialize Unity Ads
        initializeUnityAds();

        // Find buttons
        showInterstitialAdsBtn = findViewById(R.id.showInterstitialAdsBtn);
        showRewardedAdsBtn = findViewById(R.id.showRewardedAdsBtn);
        checkUpdateButton = findViewById(R.id.checkUpdateButton);

        // Set click listeners
        showInterstitialAdsBtn.setOnClickListener(v -> showInterstitialAd());
        showRewardedAdsBtn.setOnClickListener(v -> showRewardedAd());


        // Automatic update check on app start
    }

    private void initializeUnityAds() {
        UnityAds.initialize(this, UNITY_GAME_ID, false, new IUnityAdsInitializationListener() {
            @Override
            public void onInitializationComplete() {
                // Initialization successful, you can load ads here
                loadInterstitialAd();
                loadRewardedAd();
            }

            @Override
            public void onInitializationFailed(UnityAds.UnityAdsInitializationError error, String message) {
                Toast.makeText(HomeActivity.this, "Unity Ads initialization failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadInterstitialAd() {
        UnityAds.load(INTERSTITIAL_AD_UNIT_ID, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                // Ad is ready to be shown
                interstitialAdLoaded = true;
                showInterstitialAdsBtn.setEnabled(true);
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                interstitialAdLoaded = false;
                Toast.makeText(HomeActivity.this, "Interstitial ad load failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRewardedAd() {
        UnityAds.load(REWARDED_AD_UNIT_ID, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                // Ad is ready to be shown
                rewardedAdLoaded = true;
                showRewardedAdsBtn.setEnabled(true);
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                rewardedAdLoaded = false;
                Toast.makeText(HomeActivity.this, "Rewarded ad load failed: " + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInterstitialAd() {
        if (!UnityAds.isInitialized()) {
            showProgressDialog("Initializing ads...");
            initializeUnityAds();
            return;
        }

        if (!interstitialAdLoaded) {
            showProgressDialog("Loading interstitial ad...");
            loadInterstitialAd();
            return;
        }

        showProgressDialog("Preparing ad...");
        UnityAds.show(this, INTERSTITIAL_AD_UNIT_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                dismissProgressDialog();
                interstitialAdLoaded = false;
                loadInterstitialAd(); // Reload for next use

                // Open PlanetActivity
                Intent intent = new Intent(HomeActivity.this, PlanetActivity.class);
                startActivity(intent);
            }

            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                dismissProgressDialog();
                Toast.makeText(HomeActivity.this, "Ad failed: " + message, Toast.LENGTH_SHORT).show();
                loadInterstitialAd(); // Try to reload
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                dismissProgressDialog();
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                // Ad was clicked
            }
        });
    }

    private void showRewardedAd() {
        if (!UnityAds.isInitialized()) {
            showProgressDialog("Initializing ads...");
            initializeUnityAds();
            return;
        }

        if (!rewardedAdLoaded) {
            showProgressDialog("Loading rewarded ad...");
            loadRewardedAd();
            return;
        }

        showProgressDialog("Preparing rewarded ad...");
        UnityAds.show(this, REWARDED_AD_UNIT_ID, new UnityAdsShowOptions(), new IUnityAdsShowListener() {
            @Override
            public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                dismissProgressDialog();
                rewardedAdLoaded = false;
                loadRewardedAd(); // Reload for next use

                // Open ComingSoonActivity
                Intent intent = new Intent(HomeActivity.this, ComingSoonActivity.class);
                startActivity(intent);
            }

            @Override
            public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                dismissProgressDialog();
                Toast.makeText(HomeActivity.this, "Rewarded ad failed: " + message, Toast.LENGTH_SHORT).show();
                loadRewardedAd(); // Try to reload
            }

            @Override
            public void onUnityAdsShowStart(String placementId) {
                dismissProgressDialog();
            }

            @Override
            public void onUnityAdsShowClick(String placementId) {
                // Ad was clicked
            }
        });
    }

}