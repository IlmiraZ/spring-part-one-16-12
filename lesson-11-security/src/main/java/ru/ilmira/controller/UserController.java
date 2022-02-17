package ru.ilmira.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ilmira.service.RoleService;
import ru.ilmira.service.UserService;
import ru.ilmira.service.dto.UserDto;

import javax.validation.Valid;
import java.util.Optional;


@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final RoleService roleService;

    private final PasswordEncoder encoder;

    @Autowired
    public UserController(UserService userService, RoleService roleService, PasswordEncoder encoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping
    public String listPage(Model model,
                           @RequestParam("loginFilter") Optional<String> loginFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sortField") Optional<String> sort,
                           @RequestParam("dir") Optional<String> dir) {

        String sortField = sort.filter(s -> !s.isBlank()).orElse("id");
        boolean sortDir = dir.orElse("").equalsIgnoreCase(Sort.Direction.ASC.name());
        Sort sortObj = sortDir ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        model.addAttribute("products", userService.findAll(
                loginFilter,
                page.orElse(1) - 1,
                size.orElse(5),
                sortObj
        ));
        return "user";
    }


    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", userService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        return "user_form";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("user", new UserDto());
        return "user_form";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @PostMapping
    public String save(@Valid @ModelAttribute("user") UserDto user, BindingResult result) {
        if (result.hasErrors()) {
            return "user_form";
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userService.save(user);
        return "redirect:/user";
    }

    @Secured({"ROLE_ADMIN", "ROLE_SUPER_ADMIN"})
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/user";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFoundExceptionHandler(NotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "not_found";
    }
}
