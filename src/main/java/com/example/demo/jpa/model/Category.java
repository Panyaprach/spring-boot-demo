package com.example.demo.jpa.model;

import lombok.Getter;

@Getter
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
    ROMANCE("R"),
    SCI_FI("S"),
    MUSIC("M"),
    TECHNOLOGY("T");

    private final String code;

    Category(String code) {
        this.code = code;
    }

}
