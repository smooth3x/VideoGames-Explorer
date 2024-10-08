package com.example.videogames_explorer.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videogames_explorer.R;
import com.example.videogames_explorer.models.Game;

import android.content.Context;

import java.util.ArrayList;

public class gameListAdapter extends RecyclerView.Adapter<gameListAdapter.MyViewHolder> {

    private ArrayList<Game> gameList;
    private Context context;

    public gameListAdapter(Context context, ArrayList<Game> dataSet) {
        this.context = context;
        this.gameList = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textViewName;
        ImageView imageViewName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRes);
            textViewName = itemView.findViewById(R.id.nameCardView);
            imageViewName = itemView.findViewById(R.id.imageCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        /*Game selectedGame = gameList.get(position);
                        Fragment detailFragment = game_details.newInstance(selectedGame);
                        FragmentTransaction transaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView, detailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();*/

                        Game selectedGame = gameList.get(position);

                        // Create a bundle to pass the Game object
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("game", selectedGame); // Make sure Game implements Serializable

                        // Use Navigation to go to game_details
                        Navigation.findNavController(view).navigate(R.id.action_game_list_to_game_details, bundle);
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public gameListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull gameListAdapter.MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;
        textViewName.setText(gameList.get(position).getName());

        ImageView imageViewName = holder.imageViewName;

        Glide.with(context)
                .load(gameList.get(position).getImage().getOriginalUrl())
                .into(imageViewName);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void updateGameList(ArrayList<Game> newGameList) {
        gameList.clear();
        gameList.addAll(newGameList);
        notifyDataSetChanged();
    }
}

