package com.example.videogames_explorer.fragments;


import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.videogames_explorer.R;
import com.example.videogames_explorer.adapters.gameListAdapter;
import com.example.videogames_explorer.models.Game;
import com.example.videogames_explorer.services.gamesServiceAPI;

import java.util.ArrayList;

public class game_list extends Fragment {

    ArrayList<Game> arr;
    gameListAdapter adapter;

    public game_list() {}

    public static game_list newInstance(String param1, String param2) {
        game_list fragment = new game_list();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragView = inflater.inflate(R.layout.fragment_game_list, container, false);

        if (arr == null || arr.isEmpty()) {
            arr = gamesServiceAPI.getHotGames();
        }

        adapter = new gameListAdapter(requireActivity(), arr);
        SearchView searchView = fragView.findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<Game> searchResults = gamesServiceAPI.searchGames(s);
                adapter.updateGameList(searchResults);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //adapter.filter(s);
                return false;
            }
        });

        RecyclerView recyclerView = fragView.findViewById(R.id.recyclerViewRes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return fragView;
    }
}
