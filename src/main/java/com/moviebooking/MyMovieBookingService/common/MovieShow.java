package com.moviebooking.MyMovieBookingService.common;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="movie_show", uniqueConstraints = {@UniqueConstraint(columnNames = {"movieId", "screenId", "date", "startTime", "endTime"})})
public class MovieShow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieShowId;

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="screenId")
    private MultiplexScreen multiplexScreen;

    @ManyToOne
    @JoinColumn(name="cityId")
    private City city;

    @Temporal(TemporalType.DATE)
    private Date date;

    private int startTime;

    private int endTime;

    private int seatsCapacity;

    private int seatsBooked;

    private boolean bookingFull;

    private double seatCharges;
}
