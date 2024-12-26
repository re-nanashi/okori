package com.okori.workout_tracker_api.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A user POJO that represents the data that will be read from the data source.
 */
public class User {
    private @Getter @Setter long id;
    private @Getter @Setter String firstName, lastName, email, password;
    private @Getter @Setter Date createdAt, updatedAt;

    /**
     * Creates an instance of user.
     */
    public User() {}
    public User(long id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, firstName='%s', lastName='%s', email='%s']",
                id, firstName, lastName, email);
    }

    @Override
    public boolean equals(final Object that) {
        boolean isEqual = false;
        if (this == that) {
            isEqual = true;
        } else if (that != null && getClass() == that.getClass()) {
            final User user = (User) that;
            if (getId() == user.getId()) {
                isEqual = true;
            }
        }
        return isEqual;
    }
}