package app.api;


import app.model.Post;
import app.service.PostService;
import org.springframework.web.bind.annotation.*;

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


    @DeleteMapping("/{id}")
    public void handle_delete_one(@PathVariable("id") Long id) {
        postService.del_one(id);
    }

    @PostMapping
    public Post handle_post(@RequestBody Post post) {
        return postService.create_one(post);
    }

}
