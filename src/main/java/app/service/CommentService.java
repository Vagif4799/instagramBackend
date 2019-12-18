package app.service;


import app.dao.CommentRepository;
import app.model.Comment;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;


    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Optional<Comment> get_one(Long id) {
        return commentRepository.findById(id);
    }

    public Iterable<Comment> get_all() {
        return commentRepository.findAll();
    }

    public Comment create_one(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }

}
