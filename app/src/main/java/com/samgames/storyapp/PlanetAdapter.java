package com.samgames.storyapp;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.samgames.storyapp.models.PlanetModel;

import java.util.List;

public class PlanetAdapter extends RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder> {
    private List<PlanetModel> planetList;

    public PlanetAdapter(List<PlanetModel> planetList) {
        this.planetList = planetList;
    }

    @NonNull
    @Override
    public PlanetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_planet, parent, false);
        return new PlanetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetViewHolder holder, int position) {
        PlanetModel planet = planetList.get(position);
        holder.planetNameTextView.setText(planet.getPlanetName());
        holder.planetDescriptionTextView.setText(planet.getPlanetDescription());
        holder.lottieAnimationView.setAnimation(planet.getLottieAnimationResId());
        holder.lottieAnimationView.playAnimation();

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), PlanetFullDetailsActivity.class);
            intent.putExtra("PLANET_NAME", planet.getPlanetName());
            intent.putExtra("PLANET_DESCRIPTION", planet.getPlanetDescription());
            intent.putExtra("PLANET_DETAILS", planet.getPlanetDetails());
            intent.putExtra("PLANET_FACTS", planet.getPlanetFacts());
            intent.putExtra("PLANET_ANIMATION", planet.getLottieAnimationResId());
            intent.putExtra("PLANET_IMAGE", planet.getPlanetImageResId());
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return planetList.size();
    }

    static class PlanetViewHolder extends RecyclerView.ViewHolder {
        TextView planetNameTextView;
        TextView planetDescriptionTextView;
        LottieAnimationView lottieAnimationView;

        PlanetViewHolder(@NonNull View itemView) {
            super(itemView);
            planetNameTextView = itemView.findViewById(R.id.planetNameTextView);
            planetDescriptionTextView = itemView.findViewById(R.id.planetDescriptionTextView);
            lottieAnimationView = itemView.findViewById(R.id.planetLottieAnimationView);
        }
    }
}