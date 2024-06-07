package com.dnicklas.gamehub.tictactoe.bizz;

import com.dnicklas.gamehub.exceptions.InvalidTicTacToeFieldException;
import org.springframework.stereotype.Component;

@Component
public class HumanPlayer implements Player {
    private final String marker = "O";

    public String getMarker() {
        return marker;
    }

    @Override
    public void makeMove(int field, Board board) {
        if (board.getBoard().get(field) == null) {
            board.getBoard().replace(field, marker);
        } else {
            throw new InvalidTicTacToeFieldException();
        }
    }
}
