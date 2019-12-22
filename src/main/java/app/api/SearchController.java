package app.api;

import app.model.Phrase;
import app.model.User;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/search")
public class SearchController {
    private final UserService userService;

    public SearchController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public List<User> search(@RequestBody Phrase phrase)
    {
        return userService.search(phrase.getContent());
    }
}
