package app.api;


import app.model.Post;
import app.model.User;
import app.service.PostService;
import app.service.UserService;
import app.util.CurrentUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;
    private final PostService postService;

    public UsersController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping
    public Iterable<User> handle_get_all() {
        return userService.get_all();
    }

    @GetMapping("/{id}")
    public Optional<User> handle_get(@PathVariable("id") Long id){
        return userService.get_one(id);
    }

    @GetMapping("/{id}/posts")
    public List<Post> getPosts(@PathVariable("id") Long id)
    {
        return userService.getPostsByUser(id);
    }

    @GetMapping("/{id}/follows")
    public List<User> getFollows(@PathVariable("id") Long id){
        return userService.getFollowsByUser_id(id);
    }

    @GetMapping("/{id}/followers")
    public List<User> getFollowers(@PathVariable("id") Long id){
        return userService.getFollowersByUser_id(id);
    }





}



