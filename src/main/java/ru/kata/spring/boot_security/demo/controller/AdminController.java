package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping
    public String create(@ModelAttribute("create_user") User user,
                         @RequestParam("roles") Collection<Long> selectById) {
        if(user != null) {
            Collection<Role> list = new ArrayList<>();
            for (Long newRole : selectById) {
                list.addAll(roleService.getRoleById(newRole));
            }
            user.setRoles(list);
            userService.save(user);
        }
        return "redirect:/admin/allusers";
    }

    @GetMapping("/user/new")
    public String newUser(@ModelAttribute("newUser") User user, Model model, Principal principal) {
        Collection<Role> rolesList = roleService.getAllRoles();
        model.addAttribute("list_role", rolesList);
        model.addAttribute("owner", userService.getUserByName(principal.getName()));
        return "new_user";
    }

    @GetMapping("/allusers")
    public String read( Principal principal, Model model) {
        model.addAttribute("get_user", userService.read());
        model.addAttribute("owner", userService.getUserByName(principal.getName()));
        model.addAttribute("roles", roleService.getAllRoles());
        return "user_show";
    }

    @GetMapping("/user/delete/YES")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin/allusers";
    }



    @PostMapping("/user/edit")
    public String update(@ModelAttribute("User") User user,
                         @RequestParam("id") long id,
                         @RequestParam("name") String name,
                         @RequestParam("lastName") String lastname,
                         @RequestParam("age") long age,
                         @RequestParam ("email") String email,
                         @RequestParam("password") String password,
                         @RequestParam("role") Collection<Long> roleById) {
        Collection<Role> list = new ArrayList<>();
        for (Long upRole : roleById) {
            list.addAll(roleService.getRoleById(upRole));
        }
        user.setRoles(list);
        userService.update(id, name, lastname, age, email, password, list);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/userpage")
    public String userPageAdmin(@RequestParam("id") long id, Model model) {
        model.addAttribute("user_page", userService.upPage(id));
        return "user_page";
    }

}
