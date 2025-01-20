package com.moviebooking.MyMovieBookingService.common;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="movie", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "language"})})
public class Movie {

    public enum Language {
        ENGLISH,
        HINDI,
        MARATHI
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "language")
    private Language language;

    private String s3Address;

    private String detailsURL;
}
