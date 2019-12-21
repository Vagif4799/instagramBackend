package app.api;

import app.model.User;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User validate(@RequestBody User user){
        return userService.validate(user);
    }
}
