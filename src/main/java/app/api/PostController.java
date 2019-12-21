package app.api;


import app.model.Comment;
import app.model.Post;
import app.model.User;
import app.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Iterable<Post> handle_get_all() {
        return postService.get_all();
    }

    @GetMapping("/{id}")
    public Optional<Post> handle_get(@PathVariable("id") Long id) {
        return postService.get_one(id);
    }


    @GetMapping("/{id}/likes")
    public List<User> get_likes(@PathVariable("id") Long id){
        return postService.get_likes(id);
    }

    @PostMapping("/{post_id}/likes")
    public void add_like(@PathVariable("post_id") Long post_id, @RequestBody User user){
        postService.add_like(user, post_id);
    }

    @DeleteMapping("/{id}/likes")
    public void delete_like(@PathVariable("id") Long id, @RequestBody User user){
        postService.delete_like(id, user);
    }

    @GetMapping("/{id}/comments")
    public List<Comment> get_comments(@PathVariable("id") Long id){
        return postService.get_comments(id);
    }

    @PostMapping("/{post_id}/comments")
    public void add_comment(@PathVariable("post_id") Long post_id, @RequestBody Comment comment){
        postService.add_comment(comment, post_id);
    }

    @DeleteMapping("/{id}/comments")
    public void delete_comment(@PathVariable("id") Long id, @RequestBody Comment comment){
        postService.delete_comment(id, comment);
    }

}
