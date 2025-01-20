package com.moviebooking.MyMovieBookingService.booking;


import com.moviebooking.MyMovieBookingService.common.City;
import com.moviebooking.MyMovieBookingService.common.Movie;
import com.moviebooking.MyMovieBookingService.common.MovieShow;
import com.moviebooking.MyMovieBookingService.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@Data
@NoArgsConstructor
@ToString
public class MovieBookingRequest {

    private MovieShow movieShow;
    private Movie movie;
    private City city;
    private User user;
    private Set<String> seats;
}
