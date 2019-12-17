package app.api;


import app.model.User;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
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
    public Optional<User> handle_get(@PathVariable("id") int id) {
        return userService.get_one(id);
    }

    @DeleteMapping("/{id}")
    public void handle_delete_one(@PathVariable("id") int id) {
        userService.del_one(id);
    }


    @PutMapping
    public User handle_post(@RequestBody User user) {
        return userService.create_one(user);
    }


}
