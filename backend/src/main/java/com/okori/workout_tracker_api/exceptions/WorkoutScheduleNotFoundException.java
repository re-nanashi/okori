package com.okori.workout_tracker_api.exceptions;

public class WorkoutScheduleNotFoundException extends RuntimeException {
    public WorkoutScheduleNotFoundException(String message) {
        super(message);
    }
}
