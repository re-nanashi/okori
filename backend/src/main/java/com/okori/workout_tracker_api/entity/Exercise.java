package com.okori.workout_tracker_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "exercise")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;
    // TODO:
    //  [1] ideal number of sets, ideal rep ranges, actual number of sets, actual number of reps, current weight

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    @Column(name = "workout_id", nullable = false)
    private Workout workout;

    public Exercise(String name, String type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
    }
}
