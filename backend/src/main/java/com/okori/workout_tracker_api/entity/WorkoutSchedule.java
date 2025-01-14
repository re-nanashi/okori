package com.okori.workout_tracker_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "workout_schedule")
public class WorkoutSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "time", nullable = false)
    private Time time;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    @Column(name = "workout_id", nullable = false)
    private Workout workout;

    public WorkoutSchedule(Date date, Time time) {
        this.date = date;
        this.time = time;
    }
}
