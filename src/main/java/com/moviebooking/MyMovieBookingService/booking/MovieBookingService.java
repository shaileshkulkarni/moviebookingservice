package com.moviebooking.MyMovieBookingService.booking;

import com.moviebooking.MyMovieBookingService.common.MovieShow;
import com.moviebooking.MyMovieBookingService.common.MovieShowRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class MovieBookingService {

    @Autowired
    private MovieBookingDetailRepository movieBookingDetailRepository;

    @Autowired
    private MovieBookingRepository movieBookingRepository;


    @Autowired
    private MovieShowRepository movieShowRepository;


    private List<ExecutorService> executorServices = new ArrayList<>();

    private int MAX_THREADS = 10;

    @PostConstruct
    private void init(){
        for(int i=0;i<MAX_THREADS;i++){
            executorServices.add(Executors.newSingleThreadExecutor());
        }
    }

    public Future<MovieBookingResponse> addMovieBookingRequest(final MovieBookingRequest movieBookingRequest){

        // Identify the bucket to process the ticket booking for movie show in sequence.
        int bucket = (int) (movieBookingRequest.getMovieShow().getMovieShowId() % MAX_THREADS);

        Future<MovieBookingResponse> future = executorServices.get(bucket).submit(new Callable<MovieBookingResponse>() {
            @Override
            public MovieBookingResponse call() throws Exception {
                return bookMovies(movieBookingRequest);
            }
        });

        return future;
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = {SeatsNotAvailableException.class, SeatsAlreadyBookedException.class})
    public MovieBookingResponse bookMovies(MovieBookingRequest movieBookingRequest) throws SeatsNotAvailableException, SeatsAlreadyBookedException {
        System.out.println("Booking Movies for "+ movieBookingRequest);

        Optional<MovieShow> movieShowOptional = movieShowRepository.findById(movieBookingRequest.getMovieShow().getMovieShowId());

        if(movieShowOptional.isPresent() && !movieShowOptional.get().isBookingFull()) {
            MovieShow movieShow = movieShowOptional.get();
            int availableSeatCapacity = movieShow.getSeatsCapacity();
            int seatsBooked = movieShow.getSeatsBooked();
            int currentSeatCapacity = availableSeatCapacity - seatsBooked;
            int seatsToBook = movieBookingRequest.getSeats().size();
            double seatCharges = movieShow.getSeatCharges();
            double totalCost = 0;
            System.out.println("Total Seats Capacity : " + availableSeatCapacity + " : Seats Already Booked : " + seatsBooked + " : Current Seats To Book : " + seatsToBook);

            MovieBookingResponse movieBookingResponse = new MovieBookingResponse();

            try {
                if (movieBookingRequest.getSeats().size() > currentSeatCapacity) {
                    // Throw exception indicating can not book as no capacity
                    throw new SeatsNotAvailableException(currentSeatCapacity, movieBookingRequest.getSeats().size());
                } else {
                    List<MovieBookingDetail> movieBookingDetailList = new ArrayList<>();
                    // There is capacity available
                    for (String seatNo : movieBookingRequest.getSeats()) {
                        MovieBookingDetail movieBookingDetail = new MovieBookingDetail();
                        movieBookingDetail.setMovieShow(movieBookingRequest.getMovieShow());
                        movieBookingDetail.setMovie(movieBookingRequest.getMovie());
                        movieBookingDetail.setCity(movieBookingRequest.getCity());
                        movieBookingDetail.setUser(movieBookingRequest.getUser());
                        movieBookingDetail.setSeatNo(seatNo);
                        movieBookingDetail.setTicketCost(seatCharges);
                        movieBookingDetail.setTax(0d);
                        movieBookingDetail.setTotalCost(seatCharges);
                        totalCost += seatCharges;
                        // If the movie show seat is already booked then it will throw exception for
                        // violation of the unique constraint.
                        movieBookingDetailList.add(movieBookingDetailRepository.save(movieBookingDetail));
                    }
                    movieShow.setSeatsBooked(seatsBooked + seatsToBook);
                    movieShow.setBookingFull(movieShow.getSeatsBooked() == movieShow.getSeatsCapacity());

                    movieShowRepository.save(movieShow);

                    MovieBooking movieBooking = new MovieBooking();
                    movieBooking.setBookingStatus(MovieBooking.BookingStatus.BOOKED_UNPAID);
                    movieBooking.setMovie(movieBookingRequest.getMovie());
                    movieBooking.setUser(movieBookingRequest.getUser());
                    movieBooking.setTotalCost(totalCost);
                    movieBooking.setBookingDetails(movieBookingDetailList);

                    MovieBooking movieBooking2 = movieBookingRepository.save(movieBooking);
                    System.out.println("Created Movie Booking : " + movieBooking2);


                    movieBookingResponse.setMovieBooking(movieBooking2);
                    movieBookingResponse.setBookingDetailList(movieBookingDetailList);


                }
            }catch (Exception e){
                movieBookingResponse.setException(new SeatsAlreadyBookedException());
            }
            return movieBookingResponse;
        }

        return null;
    }
}
