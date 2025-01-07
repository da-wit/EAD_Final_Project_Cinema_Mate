package com.cinemamate.cinema_mate.movie.entity;

import com.cinemamate.cinema_mate.cinema.entity.Cinema;
import com.cinemamate.cinema_mate.core.base.AuditableEntity;
import com.cinemamate.cinema_mate.watchlist.entity.WatchList;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movie")
public class Movie extends AuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "title",nullable = false)
    @Setter
    private String title;
    @Column(name = "description",nullable = false,length = 1000)
    @Setter
    private String description;
    @Column(name = "duration",nullable = false)
    @Setter
    private LocalTime duration;
    @Column(name = "viewtime",nullable = false)
    @Setter
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    private LocalTime viewTime;
    @Column(name = "viewdate",nullable = false)
    @Setter
    private LocalDate viewDate;
    @Column(name = "seats",nullable = false)
    @Setter
    private Long seats;
    @Column(name = "imagepath",nullable = false,length = 1000)
    @Setter
    private String imagePath;

    @Column(name ="isActive",nullable = false)
    @Setter
    @Builder.Default
    private boolean isActive = true;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id",nullable = false)
    private Cinema cinema;

    @Setter
    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<WatchList> watchLists;

}
