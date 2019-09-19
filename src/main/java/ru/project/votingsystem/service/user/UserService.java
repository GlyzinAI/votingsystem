package ru.project.votingsystem.service.user;

import ru.project.votingsystem.model.User;
import ru.project.votingsystem.to.UserTo;
import ru.project.votingsystem.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    User getByEmail(String email) throws NotFoundException;

    void update(UserTo userTo, int id);

    List<User> getAll();
}