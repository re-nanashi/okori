package com.okori.workout_tracker_api.request;

public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String oldPassword;
    private String newPassword;

    public UserUpdateRequest() {}

    public UserUpdateRequest(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserUpdateRequest(String firstName, String lastName, String oldPassword, String newPassword) {
        this(firstName, lastName);
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}