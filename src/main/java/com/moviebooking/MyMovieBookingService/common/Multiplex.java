package com.moviebooking.MyMovieBookingService.common;


import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class Multiplex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String name;

    private String address;

    @ManyToOne
    @JoinColumn(name = "cityId")
    private City city;
}
