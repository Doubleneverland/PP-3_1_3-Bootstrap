package ru.kata.spring.boot_security.demo.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    private final RoleService roleService;
    @Autowired
    public UserDaoImp(RoleService roleService) {
        this.roleService = roleService;

    }

    public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
    @PersistenceContext
    private EntityManager entityManager;



    @Override
    @Transactional
    public void save(User user) {

        if (user != null) {

            String encoderPass = passwordEncoder().encode(user.getPassword());
            user.setPassword(encoderPass);
            entityManager.persist(user);
        }
    }

    @Override
    @Transactional
    public void create(User user, Collection<Long> selectRole) {
        if(user != null) {
            Collection<Role> list = new ArrayList<>(roleService.getRoleById(selectRole));
            user.setRoles(list);
            entityManager.persist(user);
        }
    }

    @Override
    public User getUserByName(String name) {
        List<User> query =
                entityManager.createQuery("SELECT u FROM User u where u.name = :userName", User.class)
                        .setParameter("userName", name).getResultList();
        if (!query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }

    @Override
    public List<User> read() {

        return entityManager.createQuery("from User ").getResultList();

    }

    @Override
    @Transactional
    public User update(long id, String name, String lastname, long age, String mail, String password, Collection<Role> role) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.setName(name);
            user.setLastName(lastname);
            user.setAge(age);
            user.setEmail(mail);
            String encoderPass = passwordEncoder().encode(password);
            user.setPassword(encoderPass);
            user.setRoles(role);
            entityManager.merge(user);
        }
        return user;
    }

    @Override
    public User upPage(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void delete(long id) {
        entityManager.createQuery("DELETE FROM User u WHERE u.id = :userId")
                .setParameter("userId", id)
                .executeUpdate();
    }

}
