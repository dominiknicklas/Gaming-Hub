package com.dnicklas.gamehub.tictactoe.controller;

import com.dnicklas.gamehub.account.AccountService;
import com.dnicklas.gamehub.tictactoe.bizz.Board;
import com.dnicklas.gamehub.tictactoe.bizz.GameService;
import com.dnicklas.gamehub.tictactoe.bizz.HumanPlayer;
import com.dnicklas.gamehub.tictactoe.bizz.MachinePlayer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {
    GameService gs;
    AccountService accountService;

    public GameController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/play/tictactoe")
    public String startGame(Model model) {
        gs = new GameService(new Board(), new HumanPlayer(), new MachinePlayer());
        boolean botStarted = gs.start();
        if (botStarted) {
            model.addAttribute("startMessage", "The bot made the first move now its your turn - You are 'O'");
        } else {
            model.addAttribute("startMessage", "You are 'O' - you make the first move");
        }
        model.addAttribute("board", gs.getBoard().getBoard());
        model.addAttribute("gameOverFlag", 0);
        return "tictactoe-field";
    }

    @RequestMapping("/play/tictactoe/processClick/{id}")
    public String setField(@PathVariable("id") Integer id, Model model) {
        boolean gameStatus = gs.delegateMove(id);
        if (gameStatus) {
            model.addAttribute("gameOverFlag", 1);
            if (gs.getBoard().isHumanWon()) {
                accountService.increaseTicTacToeWins();
                model.addAttribute("winningStatement", 1);
            } else if (gs.getBoard().isMachineWon()) {
                accountService.increaseTicTacToeLosses();
                model.addAttribute("winningStatement", 0);
            } else if (gs.getBoard().isDraw()) {
                accountService.increaseTicTacToeDraws();
                model.addAttribute("winningStatement", 2);
            }
        } else {
            model.addAttribute("gameOverFlag", 0);
        }
        model.addAttribute("board", gs.getBoard().getBoard());
        return "tictactoe-field";
    }
}
