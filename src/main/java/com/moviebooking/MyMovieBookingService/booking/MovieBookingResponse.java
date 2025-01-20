package com.moviebooking.MyMovieBookingService.booking;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MovieBookingResponse {
    private MovieBooking movieBooking;
    private List<MovieBookingDetail> bookingDetailList;
    private Exception exception;
}
