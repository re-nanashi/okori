package com.okori.workout_tracker_api.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class WorkoutScheduleDTO {
    private Date date;
    private Time time;
}
