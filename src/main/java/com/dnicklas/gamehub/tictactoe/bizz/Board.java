package com.dnicklas.gamehub.tictactoe.bizz;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Board {
    private Map<Integer, String> board = new HashMap<>();
    /*
    0 | 1 | 2
    3 | 4 | 5
    6 | 7 | 8
     */
    private boolean machineWon;
    private boolean humanWon;
    private boolean draw;


    public Board() {
        setUpBoard();
    }

    private void setUpBoard() {
        board.put(0, null);
        board.put(1, null);
        board.put(2, null);
        board.put(3, null);
        board.put(4, null);
        board.put(5, null);
        board.put(6, null);
        board.put(7, null);
        board.put(8, null);
    }

    public boolean checkGameOver() {
        // check for win in line
        for (int line = 0; line < 7; line+=3) {
            StringBuilder possibleWin = new StringBuilder();
            for (int column = 0; column < 3; column++) {
                String sign = board.get(column + line);
                if (sign != null) {
                    possibleWin.append(sign);
                }
            }
            if (checkForThreeInOne(possibleWin)) return true;
        }
        // check for win in colum
        for (int column = 0; column < 3; column++) {
            StringBuilder possibleWin = new StringBuilder();
            for (int line = 0; line < 7; line+=3) {
                String sign = board.get(column + line);
                if (sign != null) {
                    possibleWin.append(sign);
                }
            }
            if (checkForThreeInOne(possibleWin)) return true;
        }
        // check for diagonal win
        StringBuilder possibleWin = new StringBuilder();
        for (int diagonal = 0; diagonal < 9; diagonal+=4) {
            String sign = board.get(diagonal);
            if (sign != null) {
                possibleWin.append(sign);
            }
        }
        if (checkForThreeInOne(possibleWin)) return true;
        possibleWin = new StringBuilder();
        for (int diagonal = 2; diagonal < 7; diagonal+=2) {
            String sign = board.get(diagonal);
            if (sign != null) {
                possibleWin.append(sign);
            }
        }
        if (checkForThreeInOne(possibleWin)) return true;
        return false;
    }

    private boolean checkForThreeInOne(StringBuilder possibleWin) {
        if ("XXX".contentEquals(possibleWin)) {
            machineWon = true;
            return true;
        }
        if ("OOO".contentEquals(possibleWin)) {
            humanWon = true;
            return true;
        }
        return false;
    }

    public Map<Integer, String> getBoard() {
        return board;
    }
    public boolean isMachineWon() {
        return machineWon;
    }
    public boolean isHumanWon() {
        return humanWon;
    }

    public boolean isDraw() {
        return draw;
    }

    public void setDraw(boolean draw) {
        this.draw = draw;
    }

    public void setRemainingFields() {
        for (int counter = 0; counter < 9; counter++) {
            board.putIfAbsent(counter, "-");
        }
    }
}
