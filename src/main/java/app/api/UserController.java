package app.api;


import app.model.Post;
import app.model.User;
import app.service.PostService;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
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
    public void handle_post(@RequestBody User user) {
         userService.create_one(user);
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
    public void add_post(@PathVariable("id") Long user_id, @RequestBody Post post)
    {
        userService.get_one(user_id).ifPresent(u-> postService.create_one(u, post));
    }

    @GetMapping("/{id}/posts")
    public List<Post> getPosts(@PathVariable("id") Long id)
    {
        return userService.getPostsByUser(id);
    }

    @DeleteMapping("/{id}/posts")
    public void deletePost(@PathVariable("id") Long id, @RequestBody Post post)
    {
        postService.del_one(post.getId());
    }

    @GetMapping("/{id}/follows")
    public List<User> getFollows(@PathVariable("id") Long id){
        return userService.getFollowsByUser_id(id);
    }

    @PostMapping("/{id}/follows")
    public void add_follow(@PathVariable("id") Long id, @RequestBody User user){
        userService.add_follow(id, user);
    }

    @DeleteMapping("/{id}/follows")
    public void delete_follow(@PathVariable("id") Long id, @RequestBody User user){
        userService.del_follow(id, user);
    }

    @GetMapping("/{id}/followers")
    public List<User> getFollowers(@PathVariable("id") Long id){
        return userService.getFollowersByUser_id(id);
    }

    @GetMapping("/{id}/feed")
    public List<Post> getFeed(@PathVariable("id") Long id){
        return userService.getFeed(id);
    }

}



