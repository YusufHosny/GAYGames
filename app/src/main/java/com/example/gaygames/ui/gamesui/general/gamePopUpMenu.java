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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link gamePopUpMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class gamePopUpMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static Class<? extends AppCompatActivity> leaderboardActivity;

    private TextView tv;

    public gamePopUpMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment gamePopUpMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static gamePopUpMenu newInstance(String param1, String param2, Class<? extends AppCompatActivity> leaderboard) {
        gamePopUpMenu fragment = new gamePopUpMenu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        leaderboardActivity = leaderboard;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);

        }
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