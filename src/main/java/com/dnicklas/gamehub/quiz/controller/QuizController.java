package com.dnicklas.gamehub.quiz.controller;

import com.dnicklas.gamehub.account.AccountService;
import com.dnicklas.gamehub.quiz.api.QuizApiAccess;
import com.dnicklas.gamehub.quiz.model.Answer;
import com.dnicklas.gamehub.quiz.model.Question;
import com.dnicklas.gamehub.quiz.model.TopicAndAmount;
import com.dnicklas.gamehub.quiz.model.Topics;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class QuizController {
    private List<Question> questions = new ArrayList<>();

    private int counter = 0;

    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    private AccountService accountService;

    public QuizController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/play/quiz")
    public String gettingStarted(Model model) {
        model.addAttribute("topicAndAmount" ,new TopicAndAmount());
        model.addAttribute("topics" , Topics.values());
        return "quiz-start-page";
    }

    @PostMapping("/play/quiz/gettingStarted")
    public String playGame(@ModelAttribute("topicAndAmount") TopicAndAmount topicAndAmount, Model model) {
        questions = QuizApiAccess.getQuestions(topicAndAmount.getTopic(), topicAndAmount.getAmountOfQuestions());
        if (counter < questions.size()) {
            getNextQuestion(model);
            return "quiz-play";
        }
        return "quiz-gameOver";
    }


    @PostMapping("/play/quiz/processAnswer")
    public String processAnswer(@ModelAttribute("answer") Answer answer, Model model) {
        String userAnswer = answer.getAnswer().strip();
        String correctAnswer = answer.getCorrectAnswer().strip();
        model.addAttribute("status", 1);
        model.addAttribute("questionCounter", counter);
        if(userAnswer.equals(correctAnswer)) {
            correctAnswers++;
            model.addAttribute("answerStatus", 1);
            model.addAttribute("correctAnswerMessage", "Congratulations your answer was correct!");
        } else {
            wrongAnswers++;
            model.addAttribute("wrongAnswerMessage", "Sorry - your answer was incorrect");
            model.addAttribute("answer", answer);
            model.addAttribute("answerStatus", 0);
        }
        return "quiz-play";
    }

    @RequestMapping("/play/quiz/nextQuestion")
    public String showNextQuestion(Model model) {
        if (counter < questions.size()) {
            getNextQuestion(model);
            return "quiz-play";
        }
        if ((double) correctAnswers / (correctAnswers + wrongAnswers) >= 0.8) {
            model.addAttribute("winningFlag", 1);
            accountService.increaseQuizWins();
        } else {
            model.addAttribute("winningFlag", 0);
            accountService.increaseQuizLosses();
        }
        model.addAttribute("correctAnswers", correctAnswers);
        model.addAttribute("wrongAnswers", wrongAnswers);
        resetValues();
        return "quiz-gameOver";
    }

    private void resetValues() {
        counter = 0;
        correctAnswers = 0;
        wrongAnswers = 0;
        questions = null;
    }

    private void getNextQuestion(Model model) {
        Question question = questions.get(counter);
        List<String> answers = new ArrayList<>(List.of(question.getAnswersOne(), question.getAnswerTwo(), question.getAnswerThree(), question.getAnswerFour()));
        Collections.shuffle(answers);
        question.setAnswersOne(answers.get(0));
        question.setAnswerTwo(answers.get(1));
        question.setAnswerThree(answers.get(2));
        question.setAnswerFour(answers.get(3));
        //shuffle answers
        counter++;
        model.addAttribute("question", question);
        model.addAttribute("questionCounter", counter);
        model.addAttribute("answer", new Answer());
        model.addAttribute("status", 0);
    }
}
