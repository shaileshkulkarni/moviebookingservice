package com.moviebooking.MyMovieBookingService.booking;

import com.moviebooking.MyMovieBookingService.common.Movie;
import com.moviebooking.MyMovieBookingService.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="movie_booking")
@Data
public class MovieBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieBookingsId;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    public enum BookingStatus {
        BOOKED_UNPAID,
        BOOKED_PAYMENT_SUCCESSFUL,
        BOOKED_PAYMENT_UNSUCCESSFUL
    }

    @ManyToOne
    @JoinColumn(name="movieId")
    private Movie movie;

    @Enumerated(EnumType.ORDINAL)
    private BookingStatus bookingStatus;

    private double totalCost;

    @OneToMany(mappedBy = "movieBooking")
    private List<MovieBookingDetail> bookingDetails;
}
