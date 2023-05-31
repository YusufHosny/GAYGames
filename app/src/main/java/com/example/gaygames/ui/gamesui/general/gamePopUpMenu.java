package com.example.gaygames.ui.gamesui.general;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.gaygames.R;

import java.util.Objects;

public class gamePopUpMenu extends Fragment {
    private static Class<? extends AppCompatActivity> leaderboardActivity;

    private TextView tv;

    public gamePopUpMenu() {
        // Required empty public constructor
    }


    public static void setLeaderboardActivity(Class<? extends AppCompatActivity> leaderboard) {
        leaderboardActivity = leaderboard;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_pop_up_menu, container, false);

        ImageButton playAgain = view.findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).recreate();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();});

        ImageButton mainMenu = view.findViewById(R.id.mainMenuButton);
        mainMenu.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).finish();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();});

        ImageButton leaderb = view.findViewById(R.id.leaderboardButton);
        leaderb.setOnClickListener(v -> {
            // show leaderboard
                Intent intent = new Intent(getContext(), leaderboardActivity);
                startActivity(intent);
        });


        return view;
    }

}