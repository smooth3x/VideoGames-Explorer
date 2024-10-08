package com.example.videogames_explorer.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.videogames_explorer.R;
import com.example.videogames_explorer.models.Game;
import com.example.videogames_explorer.services.gamesServiceAPI;

import java.io.Serializable;

public class game_details extends Fragment {

    private static final String ARG_GAME = "game";
    private Game game;

    public game_details() {}

    public static game_details newInstance(Game game) {
        game_details fragment = new game_details();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME, (Serializable) game);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = (Game) getArguments().getSerializable(ARG_GAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_game_details, container, false);

        TextView nameTextView = fragView.findViewById(R.id.gameName);
        TextView deckTextView = fragView.findViewById(R.id.gameDeck);
        TextView releaseDateTextView = fragView.findViewById(R.id.gameReleaseDate);
        TextView platformsTextView = fragView.findViewById(R.id.gamePlatforms);
        ImageView imageView = fragView.findViewById(R.id.gameImage);
        Button buyFromG2AButton = fragView.findViewById(R.id.gamePurchase);
        Button gameTrailerButton = fragView.findViewById(R.id.gameTrailer);

        if (game != null) {
            nameTextView.setText(game.getName());
            deckTextView.setText(game.getDeck());
            releaseDateTextView.setText(game.getOriginalReleaseDate());

            StringBuilder platformsBuilder = new StringBuilder();
            for (Game.Platform platform : game.getPlatforms()) {
                platformsBuilder.append(platform.getName()).append("\n");
            }
            platformsTextView.setText(platformsBuilder.toString());

            Glide.with(this)
                    .load(game.getImage().getOriginalUrl())
                    .into(imageView);

            buyFromG2AButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Open G2A website to purchase the game
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.g2a.com/search?query=" + game.getName()));
                    startActivity(browserIntent);
                }
            });

            String trailerUrl = gamesServiceAPI.getGameTrailerUrl(game.getName());

            if (trailerUrl != null) {
                gameTrailerButton.setVisibility(View.VISIBLE);
                gameTrailerButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                        startActivity(intent);
                    }
                });

            } else {
                gameTrailerButton.setVisibility(View.GONE);
            }
        }
        return fragView;
    }
}

