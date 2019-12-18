package app.api;

import app.model.Comment;
import app.service.CommentService;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@RequestMapping("posts/id/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping
    public Iterable<Comment> handle_get_all() {
        return commentService.get_all();
    }

    @GetMapping("/{id}")
    public Optional<Comment> handle_get(@PathVariable("id") Long id) {
        return commentService.get_one(id);
    }

    @PostMapping
    public Comment handle_post(@RequestBody Comment comment) {
        return commentService.create_one(comment);
    }



}
