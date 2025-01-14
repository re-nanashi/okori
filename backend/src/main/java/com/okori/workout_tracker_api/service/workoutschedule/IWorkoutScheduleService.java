package com.okori.workout_tracker_api.service.workoutschedule;

import com.okori.workout_tracker_api.entity.WorkoutSchedule;
import com.okori.workout_tracker_api.exceptions.WorkoutScheduleNotFoundException;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

// TODO: Create WorkoutSchedule service
//  [1] We should create without a creation method
//  [2] Method for fetching schedule by workout ID id
//  Sample Flow: User (has an ID) can click a Workout then the user should have a
public interface IWorkoutScheduleService {
    WorkoutSchedule getWorkoutScheduleById(Long id) throws WorkoutScheduleNotFoundException;
    WorkoutSchedule updateWorkoutSchedule(WorkoutSchedule workoutSchedule) throws WorkoutScheduleNotFoundException;
    void deleteWorkoutScheduleById(Long id);
    List<WorkoutSchedule> getAllWorkoutSchedules();
    List<WorkoutSchedule> getWorkoutSchedulesByWorkoutId(Long id);
    List<WorkoutSchedule> getWorkoutSchedulesByDate(Date date);
    List<WorkoutSchedule> getWorkoutSchedulesByTime(Time time);
}