package com.samgames.storyapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;

public class PlanetFullDetailsActivity extends AppCompatActivity {

    private TextView planetNameTextView;
    private TextView planetDescriptionTextView;
    private TextView planetDetailsTextView;
    private TextView planetFactsTextView;
    private LottieAnimationView planetLottieAnimationView;
    private ImageView planetImageView;

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
    }
}