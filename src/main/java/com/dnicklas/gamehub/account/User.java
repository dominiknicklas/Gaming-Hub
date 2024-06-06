package com.dnicklas.gamehub.account;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Column(nullable = false)
    private String name;
    @Id
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column
    private int ticTacToeWins;
    @Column
    private int ticTacToeDraws;
    @Column
    private int ticTacToeLoses;
    @Column
    private int quizWins;
    @Column
    private int quizLoses;
    @Column
    private int hangmanWins;
    @Column
    private int hangmanLoses;

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.ticTacToeWins = 0;
        this.ticTacToeLoses = 0;
        this.quizWins = 0;
        this.quizLoses = 0;
        this.hangmanWins = 0;
        this.hangmanLoses = 0;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getTicTacToeWins() {
        return ticTacToeWins;
    }

    public void setTicTacToeWins(int ticTacToeWins) {
        this.ticTacToeWins = ticTacToeWins;
    }

    public int getTicTacToeLoses() {
        return ticTacToeLoses;
    }

    public void setTicTacToeLoses(int ticTacToeLoses) {
        this.ticTacToeLoses = ticTacToeLoses;
    }

    public int getTicTacToeDraws() {
        return ticTacToeDraws;
    }

    public void setTicTacToeDraws(int ticTacToeDraws) {
        this.ticTacToeDraws = ticTacToeDraws;
    }

    public int getQuizWins() {
        return quizWins;
    }

    public void setQuizWins(int quizWins) {
        this.quizWins = quizWins;
    }

    public int getQuizLoses() {
        return quizLoses;
    }

    public void setQuizLoses(int quizLoses) {
        this.quizLoses = quizLoses;
    }

    public int getHangmanWins() {
        return hangmanWins;
    }

    public void setHangmanWins(int hangmanWins) {
        this.hangmanWins = hangmanWins;
    }

    public int getHangmanLoses() {
        return hangmanLoses;
    }

    public void setHangmanLoses(int hangmanLoses) {
        this.hangmanLoses = hangmanLoses;
    }
}
