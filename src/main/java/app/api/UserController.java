package app.api;


import app.model.Post;
import app.model.User;
import app.service.PostService;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }


    @GetMapping
    public Iterable<User> handle_get_all() {
        return userService.get_all();
    }

    @PostMapping
    public User handle_post(@RequestBody User user) {
        return userService.create_one(user);
    }

    @GetMapping("/{id}")
    public Optional<User> handle_get(@PathVariable("id") Long id) {
        return userService.get_one(id);
    }

    @DeleteMapping("/{id}")
    public void handle_delete_one(@PathVariable("id") Long id) {
        userService.del_one(id);
    }

    @PutMapping("/{id}")
    public User handle_put(@PathVariable("id") Long id, @RequestBody User user) {
        return userService.update_user(user, id);
    }

    @PostMapping("/{id}/posts")
    public Post add_post(@PathVariable("id") Long id, @RequestBody Post post)
    {
        userService.get_one(id).ifPresent(post::setUser);
        return postService.create_one(post);
    }

    @GetMapping("/{id}/posts")
    public Set<Post> getPosts(@PathVariable("id") Long id)
    {
        return userService.getPostsByUser(id);
    }
}
