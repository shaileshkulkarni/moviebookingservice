package com.moviebooking.MyMovieBookingService.booking;

import com.moviebooking.MyMovieBookingService.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieBookingRepository extends CrudRepository<MovieBooking, Long> {

    public List<MovieBooking> findByUser(User user);
}
