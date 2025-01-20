package com.moviebooking.MyMovieBookingService.booking;

public class SeatsNotAvailableException extends Exception {

    private int availableSeats;
    private int requestedSeats;

    public SeatsNotAvailableException(int availableSeats, int requestedSeats){
        this.availableSeats = availableSeats;
        this.requestedSeats = requestedSeats;
    }
}
