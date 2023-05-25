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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static Class<? extends AppCompatActivity> leaderboardActivity;

    private ImageButton playAgain,mainMenu,leaderb;
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_pop_up_menu, container, false);

        playAgain = view.findViewById(R.id.playAgainButton);
        playAgain.setOnClickListener(v -> {getActivity().recreate();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();});

        mainMenu= view.findViewById(R.id.mainMenuButton);
        mainMenu.setOnClickListener(v -> {getActivity().finish();
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();});

        leaderb = view.findViewById(R.id.leaderboardButton);
        leaderb.setOnClickListener(v -> {
            // show leaderboard
                Intent intent = new Intent(getContext(), leaderboardActivity);
                startActivity(intent);
        });




        return view;
    }

}