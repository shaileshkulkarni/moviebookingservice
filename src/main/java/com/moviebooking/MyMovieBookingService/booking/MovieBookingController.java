package com.moviebooking.MyMovieBookingService.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/moviebookings")
public class MovieBookingController {

    @Autowired
    private MovieBookingService movieBookingService;

    @PutMapping
    public MovieBookingResponse bookMovie(@RequestBody MovieBookingRequest movieBookingRequest){
        System.out.println("Trying to book movie tickets for Request : " + movieBookingRequest);
        try {
//            List<MovieBookingDetail> movieBookingDetailsList =  movieBookingService.bookMovies(movieBookingRequest);
            Future<MovieBookingResponse> future = movieBookingService.addMovieBookingRequest(movieBookingRequest);
            MovieBookingResponse movieBookingResponse = future.get(20,TimeUnit.SECONDS);
            return movieBookingResponse;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }
}
