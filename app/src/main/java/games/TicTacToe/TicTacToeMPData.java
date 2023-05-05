package games.TicTacToe;

import java.util.ArrayList;

public class TicTacToeMPData {
    // current tictactoe multiplayer match id if relevant
    public static int currentMatchId;

    public static TicTacToeChoices choice;

    // list containing all the multiplayer match codes
    public static ArrayList<String> codeList = new ArrayList<>();

    // get the code by the match id
    public static String getCode(int id) {
        return codeList.get(id);
    }

    // get match id via the code
    public static int getMatchIdByCode(String code) {
        return codeList.indexOf(code);
    }

    // generate the unique match codes
    public static void genCodeList() {
        for(int i = 0; i < 100; i++) {
            int x = Math.abs((i + "aeasg awt").hashCode());
            StringBuilder s = new StringBuilder();
            for(int j = 0; j < 4; j++) {
            s.append(getCharForNumber(x % 25 + 1));
            x /= 100;
        }

        if(codeList.contains(s.toString())) System.out.println("L");
        System.out.print(s +",");
        codeList.add(s.toString());
        }
    }

    // get a char from a numerical input
    private static String getCharForNumber(int i) {
        return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
    }

    public static int getCurrentMatchId() {
        return currentMatchId;
    }

    public static void setCurrentMatchId(int currentMatchId) {
        TicTacToeMPData.currentMatchId = currentMatchId;
    }
}
