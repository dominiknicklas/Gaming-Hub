package com.dnicklas.gamehub.tictactoe.bizz;

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
            // TODO: make custom exception and handle with HTTP code
            throw new RuntimeException();
        }
    }
}
