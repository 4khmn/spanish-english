package com.example.spanish.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "words")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String english;

    @Column(nullable = false)
    private String spanish;

    private int unit;

    @Column(name = "part_of_speech", nullable = false)
    private String partOfSpeech;

    private String category;
}