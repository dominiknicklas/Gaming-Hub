package com.dnicklas.gamehub.quiz.model;

public class Question {
    private String question;

    private String answersOne;

    private String answerTwo;

    private String answerThree;

    private String answerFour;

    private String correctAnswer;

    public Question(String question, String answersOne, String answerTwo, String answerThree, String answerFour, String correctAnswer, String topic) {
        this.question = question;
        this.answersOne = answersOne;
        this.answerTwo = answerTwo;
        this.answerThree = answerThree;
        this.answerFour = answerFour;
        this.correctAnswer = correctAnswer;
    }

    public Question() {

    }
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswersOne() {
        return answersOne;
    }

    public void setAnswersOne(String answersOne) {
        this.answersOne = answersOne;
    }

    public String getAnswerTwo() {
        return answerTwo;
    }

    public void setAnswerTwo(String answerTwo) {
        this.answerTwo = answerTwo;
    }

    public String getAnswerThree() {
        return answerThree;
    }

    public void setAnswerThree(String answerThree) {
        this.answerThree = answerThree;
    }

    public String getAnswerFour() {
        return answerFour;
    }

    public void setAnswerFour(String answerFour) {
        this.answerFour = answerFour;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

}
