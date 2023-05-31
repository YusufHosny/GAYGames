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

    // Makes leaderboardActivity field equal to leaderboard
    public static void setLeaderboardActivity(Class<? extends AppCompatActivity> leaderboard) {
        leaderboardActivity = leaderboard;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment = makes it visible
        View view = inflater.inflate(R.layout.fragment_game_pop_up_menu, container, false);

        // Play again button
        ImageButton playAgain = view.findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).recreate(); // Restarts the activity
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();}); // Removes the pop up

        // Main Menu button
        ImageButton mainMenu = view.findViewById(R.id.mainMenuButton);
        mainMenu.setOnClickListener(v -> {
            Objects.requireNonNull(getActivity()).finish(); // Finishes the activity
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();}); // Removes pop up

        ImageButton LBbutton = view.findViewById(R.id.leaderboardButton);
        LBbutton.setOnClickListener(v -> {
            // show leaderboard
                Intent intent = new Intent(getContext(), leaderboardActivity);
                startActivity(intent); // Go to leaderboard activity
        });

        return view;
    }

}