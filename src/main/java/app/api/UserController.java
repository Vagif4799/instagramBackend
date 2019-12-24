package app.api;

import app.model.Post;
import app.model.User;
import app.service.PostService;
import app.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService, PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @DeleteMapping
    public void handle_delete_one() {
        userService.del_one();
    }

    @PostMapping("/posts")
    public void add_post(@RequestBody Post post)
    {
        postService.create_one(post);
    }

    @DeleteMapping("/posts")
    public void deletePost(@RequestBody Post post)
    {
        postService.del_one(post.getId());
    }

    @PostMapping("/follows")
    public void add_follow(@RequestBody User user){
        userService.add_follow(user);
    }

    @DeleteMapping("/follows")
    public void delete_follow(@RequestBody User user){
        userService.del_follow(user);
    }

    @GetMapping("/recommend")
    public List<User> getRecommendations(){
        return userService.getRecommended();
    }

    @GetMapping("/feed")
    public List<Post> getFeed(){
        return userService.getFeed();
    }


}
