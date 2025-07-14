package com.okori.workout_tracker_api.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "workout")
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "category", nullable = false)
    private String category;
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Exercise> exercises = new ArrayList<>();
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutSchedule> workoutSchedules = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Workout() {}

    public Workout(String name, String category, String description, User user) {
        this.name = name;
        this.category = category;
        this.description = description;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<WorkoutSchedule> getWorkoutSchedules() {
        return workoutSchedules;
    }

    public void setWorkoutSchedules(List<WorkoutSchedule> workoutSchedules) {
        this.workoutSchedules = workoutSchedules;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}