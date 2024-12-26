package com.okori.workout_tracker_api.dao;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Dao Interface
 * TODO: Create custom exceptions
 */
public interface Dao<T> {
    /**
     * @return all the users as a stream. The stream may be lazily or eagerly evaluated based
     * on the implementation. The stream must be closed after use.
     * @throws Exception if any error occurs.
     */
    Stream<T> getAll() throws Exception;

    /**
     * @param id unique identifier of the user.
     * @return an optional with user if a user with unique identifier <code>id</code>
     *     exists, empty optional otherwise.
     * @throws Exception if any error occurs.
     */
    Optional<T> getById(long id) throws Exception;

    /**
     * @param t the user to be added.
     * @return true if user is successfully added, false if user already exists.
     * @throws Exception if any error occurs.
     */
    boolean add(T t) throws Exception;

    /**
     * @param t the user to be updated.
     * @return true if user is successfully updated, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean update(T t) throws Exception;

    /**
     * @param t the user to be deleted.
     * @return true if user is successfully deleted, false otherwise.
     * @throws Exception if any error occurs.
     */
    boolean delete(T t) throws Exception;
}