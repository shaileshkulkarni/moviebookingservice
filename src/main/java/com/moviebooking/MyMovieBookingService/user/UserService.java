package com.moviebooking.MyMovieBookingService.user;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public User addUser(User user){
        return userRepository.save(user);
    }
}
