package com.dnicklas.gamehub.hangman.controller;

import com.dnicklas.gamehub.hangman.bizz.Answer;
import com.dnicklas.gamehub.hangman.bizz.GameLogic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HangmanController {
    GameLogic gl;

    public HangmanController(GameLogic gl) {
        this.gl = gl;
    }

    @GetMapping("/play/hangman")
    public String showStartingPage(Model model) {
        gl.startNewRound();
        System.out.println(gl.getWord());
        model.addAttribute("progress", gl.getCurrentStatus());
        model.addAttribute("logAnswer", new Answer());
        guessAndStat(model, 0);
        return "hangman-page";
    }


    @PostMapping("/play/hangman/processAnswer")
    public String getAnswer(@ModelAttribute("logAnswer") Answer answer, Model model) {
        char c = answer.getAnswer().charAt(0);
        String process = gl.checkIfGuessIsCorrect(c);
        if (gl.gameOver() == 0) {
            model.addAttribute("progress", process);
            model.addAttribute("logAnswer", new Answer());
            guessAndStat(model, 0);
        } else if (gl.gameOver() == 1) {
            model.addAttribute("correctWord", String.format("The correct word was: %s", gl.getWord()));
            model.addAttribute("message", "Sorry - you didn't win");
            guessAndStat(model, 1);
        } else {
            model.addAttribute("message", "Congratulations you've won");
            guessAndStat(model, 2);
        }
        return "hangman-page";
    }

    private void guessAndStat(Model model, int status) {
        model.addAttribute("guesses", gl.getGuesses());
        model.addAttribute("status", status);
    }
}
