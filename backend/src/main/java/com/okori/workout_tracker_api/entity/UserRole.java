package com.okori.workout_tracker_api.entity;

public enum UserRole {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String text;

    UserRole(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}