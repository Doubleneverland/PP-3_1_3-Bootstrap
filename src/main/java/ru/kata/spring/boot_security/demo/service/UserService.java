package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserService {
    void save(User user);

    List<User> read();

    User getUserByName(String name);

    void delete(long id);

    User update(long id, String name, String lastname,long age, String mail, String password, Collection<Long> role);

    User upPage(long id);

    void create(User user, Collection<Long> selectRole);

}
