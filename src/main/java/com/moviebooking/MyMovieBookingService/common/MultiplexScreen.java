package com.moviebooking.MyMovieBookingService.common;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@NoArgsConstructor
@Table(name = "multiplex_screen",uniqueConstraints = @UniqueConstraint(columnNames = {"multiplexId, screenName"}))
public class MultiplexScreen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;

    private String screenName;

    @ManyToOne
    @JoinColumn(name="multiplexId")
    private Multiplex multiplex;

    private int totalCapacity;

}
