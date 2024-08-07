package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Collection;

public interface RoleDao {

    void saveRole(Role role);

    Role getRoleByName(String roleName);

    Collection<Role> getAllRoles();

    Collection<Role> getRoleById(Collection<Long> roleById);
}
