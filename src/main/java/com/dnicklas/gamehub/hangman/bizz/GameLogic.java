package com.dnicklas.gamehub.hangman.bizz;

import com.dnicklas.gamehub.account.AccountService;
import com.dnicklas.gamehub.hangman.api.HangmanApiAccess;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GameLogic {
    private String word;
    private String currentStatus;
    private int guesses;

    private AccountService accountService;

    public GameLogic(AccountService accountService) {
        this.accountService = accountService;
    }

    public void startNewRound() {
        word = HangmanApiAccess.getWord();
        guesses = 0;
        currentStatus = null;
        for (int counter = 0; counter < word.length(); counter++) {
            if(currentStatus == null) {
                currentStatus = "_";
            } else {
                currentStatus = currentStatus.concat("_");
            }
        }
    }

    public String getCurrentStatus() {
        StringBuilder sb = new StringBuilder();

        for (char c: currentStatus.toCharArray()) {
            sb.append(c).append(" ");
        }
        return sb.toString();
    }

    public String checkIfGuessIsCorrect(char guess) {
        List<Integer> indexes = new ArrayList<>();
        int index = word.indexOf(guess);
        while (index >= 0) {
            indexes.add(index);
            index = word.indexOf(guess, index + 1);
        }
        if(!indexes.isEmpty()) {
            StringBuilder myString = new StringBuilder(currentStatus);
            for (int counter = 0; counter < indexes.size(); counter++) {
                myString.setCharAt(indexes.get(counter), guess);
            }
            currentStatus = myString.toString();
        } else {
            guesses++;
        }
        return getCurrentStatus();
    }

    public int gameOver() {
        // 1 -> player lost
        // 2 -> player won
        // 0 -> game not over
        if (guesses == 11) {
            accountService.increaseHangmanLosses();
            return 1;
        }
        if (word.equals(currentStatus)) {
            accountService.increaseHangmanWins();
            return 2;
        }
        return 0;
    }

    public int getGuesses() {
        return guesses;
    }

    public String getWord() {
        return word;
    }

}
