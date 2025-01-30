package com.samgames.storyappx.models;

public class PlanetModel {
    private String planetName;
    private String planetDescription;
    private String planetDetails;
    private String planetFacts;
    private int lottieAnimationResId;
    private int planetImageResId;

    public PlanetModel(String planetName, String planetDescription,
                       String planetDetails, String planetFacts,
                       int lottieAnimationResId, int planetImageResId) {
        this.planetName = planetName;
        this.planetDescription = planetDescription;
        this.planetDetails = planetDetails;
        this.planetFacts = planetFacts;
        this.lottieAnimationResId = lottieAnimationResId;
        this.planetImageResId = planetImageResId;
    }

    // Getters
    public String getPlanetName() { return planetName; }
    public String getPlanetDescription() { return planetDescription; }
    public String getPlanetDetails() { return planetDetails; }
    public String getPlanetFacts() { return planetFacts; }
    public int getLottieAnimationResId() { return lottieAnimationResId; }
    public int getPlanetImageResId() { return planetImageResId; }
}