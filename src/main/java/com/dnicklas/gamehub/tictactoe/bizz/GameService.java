package com.dnicklas.gamehub.tictactoe.bizz;

import org.springframework.stereotype.Service;

@Service
public class GameService {
    Board board;
    int moves;
    Player humanPlayer;
    Player machinePlayer;


    public GameService(Board board, Player humanPlayer, Player machinePlayer) {
        this.board = board;
        this.moves = 0;
        this.humanPlayer = humanPlayer;
        this.machinePlayer = machinePlayer;
    }
    public Board getBoard() {
        return board;
    }

    private boolean randomizeStartPlayer() {
        int randomize = (Math.random() <= 0.5) ? 1 : 2;
        if(randomize == 1) {
            machinePlayer.makeMove(0, board);
            moves++;
            return true;
        }
        return false;
    }

    public boolean delegateMove(int field) {
        boolean status = false;
        if (moves < 9) {
            humanPlayer.makeMove(field, board);
            moves++;
            status = board.checkGameOver();
        }
        if (moves < 9 && !status) {
            machinePlayer.makeMove(0, board);
            moves++;
            status = board.checkGameOver();
        }
        if (moves == 9 && !status) {
            status = true;
            board.setDraw(true);
        }
        if (status) board.setRemainingFields();
        return status;
    }

    public boolean start() {
        return randomizeStartPlayer();
    }
}
