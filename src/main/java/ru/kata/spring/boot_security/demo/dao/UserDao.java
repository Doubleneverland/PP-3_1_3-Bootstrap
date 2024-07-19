package ru.kata.spring.boot_security.demo.dao;




import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;

public interface UserDao {


    void save(User user);

    List<User> read();

    User update(long id, String name, String lastname, long age, String mail, String password, Collection<Role> role);

    void delete(long id);

    User getUserByName(String name);

    User upPage(long id);

    void create(User user, Collection<Long> selectRole);

}
