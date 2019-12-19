package app.api;


import app.model.Post;
import app.service.CommentService;
import app.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    public PostController(PostService postService, CommentService commentService) {
        this.postService = postService;
        this.commentService = commentService;
    }


    @GetMapping
    public Iterable<Post> handle_get_all() {
        return postService.get_all();
    }

    @GetMapping("/{id}")
    public Optional<Post> handle_get(@PathVariable("id") Long id) {
        return postService.get_one(id);
    }


    @DeleteMapping("/{id}")
    public void handle_delete_one(@PathVariable("id") Long id) {
        postService.del_one(id);
    }

}
