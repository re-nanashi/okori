package com.okori.workout_tracker_api.dao;

import com.okori.workout_tracker_api.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

public class UserDao implements Dao<User> {
    // TODO: https://www.javaguides.net/2018/08/data-access-object-pattern-in-java.html
    private List<User> users = new ArrayList<>();

    public UserDao() {
    }

    @Override
    public Optional<User> get(long id) {
        return Optional.ofNullable(users.get((int) id));
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public void save(User user) {
        users.add(user);
    }

    @Override
    public void update(User user, String[] params) {
        user.setName(Objects.requireNonNull(
                params[0], "Name cannot be null"));
        user.setEmail(Objects.requireNonNull(
                params[1], "Email cannot be null"));

        users.add(user);
    }

    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
