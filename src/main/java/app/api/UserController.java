package app.api;


import app.model.User;
import app.service.UserService;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Iterable<User> handle_get_all() {
        return userService.get_all();
    }

    @GetMapping("/{id}")
    public Optional<User> handle_get(@PathVariable("id") Long id) {
        return userService.get_one(id);
    }

    @DeleteMapping("/{id}")
    public void handle_delete_one(@PathVariable("id") Long id) {
        userService.del_one(id);
    }


    @PutMapping
    public User handle_post(@RequestBody User user) {
        return userService.create_one(user);
    }

    @PostMapping
    public String UserControllerChecker (@Valid User user, Errors errors) {
        if (errors.hasErrors())  {
            return "login";
        }
        return "redirect:/";
    }


}
