package com.example.demo.model;

public enum Category {
    ACTION("Ac"),
    ANIMATION("A"),
    ADVENTURE("Ad"),
    CRIME("Cr"),
    COMEDY("C"),
    DRAMA("D"),
    DOCUMENTARIES("Do"),
    EROTIC("E"),
    FAMILY("Fa"),
    FANTASY("F"),
    GENRE("G"),
    ROMANTIC("R"),
    SCI_FI("S"),
    MUSIC("M"),
    TECHNOLOGY("T");

    private String code;

    Category(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
