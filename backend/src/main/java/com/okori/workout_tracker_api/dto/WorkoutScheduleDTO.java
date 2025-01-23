package com.okori.workout_tracker_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.sql.Time;

public class WorkoutScheduleDTO {
    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private Time time;

    public WorkoutScheduleDTO() {}
    public WorkoutScheduleDTO(Long id, Date date, Time time) {
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}