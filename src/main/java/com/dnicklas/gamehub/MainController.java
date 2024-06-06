package com.dnicklas.gamehub;

import com.dnicklas.gamehub.account.AccountService;
import com.dnicklas.gamehub.account.User;
import com.dnicklas.gamehub.exceptions.DuplicateUserException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class MainController {
    private final AccountService accountService;

    public MainController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String showStartPage() {
        return "start";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/processRegistration")
    public String processRegister(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        try {
            accountService.safeUser(user);
            return "register-success";
        } catch (DuplicateUserException e) {
            model.addAttribute("error", e.getMessage());
            User name = new User();
            name.setName(user.getName());
            model.addAttribute("user", name);
            return "register";

        }
    }

    @GetMapping("/processLogin")
    public String showGames(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        String username = userDetails.getUsername();
        accountService.setCurrentUser(accountService.getUser(username));
        model.addAttribute("name", accountService.getCurrentUser().getName());
        return "home-page";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        model.addAttribute("name", accountService.getCurrentUser().getName());
        return "home-page";
    }


    @GetMapping("/home/account")
    public String showAccountInformation(Model model) {
        User currentUser = accountService.getCurrentUser();
        model.addAttribute("name", currentUser.getName());
        model.addAttribute("email", currentUser.getEmail());
        List<List<Object>> tictactoeData = new java.util.ArrayList<>(getChartData(currentUser.getTicTacToeWins(), currentUser.getTicTacToeLoses()));
        tictactoeData.add(List.of("Draws", currentUser.getTicTacToeDraws()));
        model.addAttribute("chartDataTicTacToe", tictactoeData);
        model.addAttribute("chartDataQuiz", getChartData(currentUser.getQuizWins(), currentUser.getQuizLoses()));
        model.addAttribute("chartDataHangman", getChartData(currentUser.getHangmanWins(), currentUser.getHangmanLoses()));
        return "account-page";
    }

    private List<List<Object>> getChartData(int wins, int loses) {
        return List.of(
                List.of("Wins", wins),
                List.of("Loses", loses)
        );
    }
}
