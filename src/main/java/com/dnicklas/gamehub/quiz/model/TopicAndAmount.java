package com.dnicklas.gamehub.quiz.model;

public class TopicAndAmount {
    private Topics topic;
    private String amountOfQuestions;

    public Topics getTopic() {
        return topic;
    }

    public void setTopic(Topics topic) {
        this.topic = topic;
    }

    public String getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(String amountOfQuestions) {
        this.amountOfQuestions = amountOfQuestions;
    }
}
