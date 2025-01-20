package com.moviebooking.MyMovieBookingService.booking;

import com.moviebooking.MyMovieBookingService.common.City;
import com.moviebooking.MyMovieBookingService.common.Movie;
import com.moviebooking.MyMovieBookingService.common.MovieShow;
import com.moviebooking.MyMovieBookingService.user.User;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"movieShowId","seatNo"}))
public class MovieBookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "movie_booking_id")
    private MovieBooking movieBooking;

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name="cityId")
    private City city;

    @ManyToOne
    @JoinColumn(name="movieShowId")
    private MovieShow movieShow;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    private String seatNo;

    private double ticketCost;

    private double tax;

    private double totalCost;
}
