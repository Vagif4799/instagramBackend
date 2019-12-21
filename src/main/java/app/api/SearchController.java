package app.api;

import app.model.User;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {
    private final UserService userService;

    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User search(@RequestBody User user)
    {
        return userService.searchByUserName(user.getUsername());
    }
}
