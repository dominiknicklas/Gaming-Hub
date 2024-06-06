package com.dnicklas.gamehub.tictactoe.bizz;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class MachinePlayer implements Player {
    private final String marker = "X";
    @Override
    public void makeMove(int field, Board board) {
        int trueField = searchForBestOption(board);
        if (board.getBoard().get(trueField) == null) {
            board.getBoard().replace(trueField, marker);
        } else {
            // TODO: make custom exception and handle with HTTP code
            throw new RuntimeException();
        }
    }

    private int searchForBestOption(Board board) {
        // check if two XX in one line / column / diagonal and make wining move
        List<Integer> towFriendlyLine = checkForTwoInOneLine(board, "XX");
        if (!towFriendlyLine.isEmpty()) return towFriendlyLine.getFirst();
        List<Integer> towFriendlyColumn = checkForTwoInOneColumn(board, "XX");
        if (!towFriendlyColumn.isEmpty()) return towFriendlyColumn.getFirst();
        List<Integer> towFriendlyDiagonal = checkForTwoInDiagonal(board, "XX");
        if (!towFriendlyDiagonal.isEmpty()) return towFriendlyDiagonal.getFirst();

        // check if two OO in one line / column / diagonal and block enemy win
        List<Integer> twoEnemyLine = checkForTwoInOneLine(board, "OO");
        if (!twoEnemyLine.isEmpty()) return twoEnemyLine.getFirst();
        List<Integer> twoEnemyColumn = checkForTwoInOneColumn(board, "OO");
        if (!twoEnemyColumn.isEmpty()) return twoEnemyColumn.getFirst();
        List<Integer> twoEnemyDiagonal = checkForTwoInDiagonal(board, "OO");
        if (!twoEnemyDiagonal.isEmpty()) return twoEnemyDiagonal.getFirst();

        // get random field
        int randomField;
        Random random = new Random();
        do {
            randomField = random.nextInt(9);
        } while (board.getBoard().get(randomField) != null);
        return randomField;
    }

    public List<Integer> checkForTwoInOneLine(Board board, String type) {
        List<Integer> possibleFields = new ArrayList<>();
        for (int line = 0; line < 7; line+=3) {
            StringBuilder possibleWin = new StringBuilder();
            Integer possibleChoice = null;
            for (int column = 0; column < 3; column++) {
                String sign = board.getBoard().get(column + line);
                if (sign != null) possibleWin.append(sign);
                if (sign == null) possibleChoice = column + line;
            }
            if (type.contentEquals(possibleWin) && possibleChoice != null) possibleFields.add(possibleChoice);
        }
        return possibleFields;
    }

    public List<Integer> checkForTwoInOneColumn(Board board, String type) {
        List<Integer> possibleFields = new ArrayList<>();
        for (int column = 0; column < 3; column++) {
            StringBuilder possibleWin = new StringBuilder();
            Integer possibleChoice = null;
            for (int line = 0; line < 7; line+=3) {
                String sign = board.getBoard().get(column + line);
                if (sign != null) possibleWin.append(sign);
                if (sign == null) possibleChoice = column + line;
            }
            if (type.contentEquals(possibleWin) && possibleChoice != null) possibleFields.add(possibleChoice);
        }
        return possibleFields;
    }

    public List<Integer> checkForTwoInDiagonal(Board board, String type) {
        StringBuilder possibleWin = new StringBuilder();
        List<Integer> possibleFields = new ArrayList<>();
        Integer possibleChoice = null;
        for (int diagonal = 0; diagonal < 9; diagonal+=4) {
            String sign = board.getBoard().get(diagonal);
            if (sign != null) possibleWin.append(sign);
            if (sign == null) possibleChoice = diagonal;
        }
        if (type.contentEquals(possibleWin) && possibleChoice != null) possibleFields.add(possibleChoice);
        possibleWin = new StringBuilder();
        possibleChoice = null;
        for (int diagonal = 2; diagonal < 7; diagonal+=2) {
            String sign = board.getBoard().get(diagonal);
            if (sign != null) possibleWin.append(sign);
            if (sign == null) possibleChoice = diagonal;
        }
        if (type.contentEquals(possibleWin) && possibleChoice != null) possibleFields.add(possibleChoice);
        return possibleFields;
    }
}
