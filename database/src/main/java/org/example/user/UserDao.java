package org.example.user;

import exceptions.NoSuchUserException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id) throws NoSuchUserException;

    void changeNameById(long id, String newName) throws NoSuchUserException;

    void AddNewUser(String path, User user);

    List<User> getUsers(String path);

    void writeUsers(String path, List<User> users);
}
