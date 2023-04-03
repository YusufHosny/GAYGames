package games.TicTacToe;

import android.widget.Button;

public class TicTacToeTile {

    private TicTacToeChoices choice;



    private Button button;


    public TicTacToeTile(Button b) {
        button = b;
        choice = TicTacToeChoices.BLANK;

    }

    public void playTile(TicTacToeChoices c) {
        choice = c;

        button.setText(getChoiceString(c));
    }

    public static String getChoiceString(TicTacToeChoices c) {
        String s;
        switch(c) {
            case X:
                s = "X";
                break;

            case O:
                s = "O";
                break;

            default:
                s = "";
                break;
        }
        return s;
    }

    public TicTacToeChoices getChoice() {
        return choice;
    }

    public Button getButton() {
        return button;
    }
}
