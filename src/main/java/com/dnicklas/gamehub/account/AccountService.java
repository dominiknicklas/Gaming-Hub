package com.dnicklas.gamehub.account;

import com.dnicklas.gamehub.exceptions.DuplicateUserException;
import com.dnicklas.gamehub.exceptions.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    private final UserRepository userRepository;

    private User currentUser;

    public AccountService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void safeUser(User user) throws DuplicateUserException {
        if (userRepository.existsUserByEmail(user.getEmail())) throw new DuplicateUserException("The given email already exists!");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public User getUser(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail.isPresent()) {
            return userByEmail.get();
        } else {
            throw new UserNotFoundException("There is no User with email: " + email);
        }
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void increaseTicTacToeWins() {
        int newWins = currentUser.getTicTacToeWins() + 1;
        currentUser.setTicTacToeWins(newWins);
        updateUser(currentUser);
    }

    public void increaseTicTacToeDraws() {
        int newDraws = currentUser.getTicTacToeDraws() + 1;
        currentUser.setTicTacToeDraws(newDraws);
        updateUser(currentUser);
    }

    public void increaseTicTacToeLosses() {
        int newLosses = currentUser.getTicTacToeLoses() + 1;
        currentUser.setTicTacToeLoses(newLosses);
        updateUser(currentUser);
    }

    public void increaseQuizWins() {
        int newWins = currentUser.getQuizWins() + 1;
        currentUser.setQuizWins(newWins);
        updateUser(currentUser);
    }

    public void increaseQuizLosses() {
        int newLosses = currentUser.getQuizLoses() + 1;
        currentUser.setQuizLoses(newLosses);
        updateUser(currentUser);
    }

    public void increaseHangmanWins() {
        int newWins = currentUser.getHangmanWins() + 1;
        currentUser.setHangmanWins(newWins);
        updateUser(currentUser);
    }

    public void increaseHangmanLosses() {
        int newLosses = currentUser.getHangmanLoses() + 1;
        currentUser.setHangmanLoses(newLosses);
        updateUser(currentUser);
    }
}
