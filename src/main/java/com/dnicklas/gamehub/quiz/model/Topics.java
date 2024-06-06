package com.dnicklas.gamehub.quiz.model;

public enum Topics {
    general_knowledge("General Knowledge"),
    science("Science"),
    geography("Geography"),
    art_and_literature("Art and Literature"),
    history("History");

    private final String stringValue;
    private Topics(String stringValue) {
        this.stringValue = stringValue;
    }
    public String getStringValue() {
        return stringValue;
    }
}
