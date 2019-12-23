package app.service;

import app.dao.CommentRepository;
import app.dao.PostRepository;
import app.model.Comment;
import app.model.Post;
import app.model.User;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Optional<Post> get_one(Long id) {
        return postRepository.findById(id);
    }

    public Iterable<Post> get_all() {
        return postRepository.findAll();
    }

    public Post create_one(User user, Post post) {
        post.setUser(user);
        postRepository.save(post);
        return post;
    }

    public void del_one(Long id) {
        postRepository.deleteById(id);
    }

    public Post save_changes(Post post) {
        postRepository.save(post);
        return post;
    }

    public int number_of_comments(Long id) {
        Optional<Post> byId = postRepository.findById(id);
        return byId.get().getComments().size();

    }


    public List<User> get_likes(Long id) {
        return postRepository.findById(id).map(Post::getLikes).get();
    }

    public void add_like(User user, Long post_id) {
        postRepository.findById(post_id).ifPresent(post -> {
            post.getLikes().add(user);
            postRepository.save(post);
        });
    }

    public void delete_like(Long id, User user) {
        postRepository.findById(id).ifPresent(post->{
            post.getLikes().remove(user);
            postRepository.save(post);
        });
    }

    public List<Comment> get_comments(Long id) {
        return postRepository.findById(id).map(Post::getComments)
                .map(list->list.stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .collect(Collectors.toList()))
                .get();
    }

    public void add_comment(Comment comment, Long post_id) {
        postRepository.findById(post_id).ifPresent(comment::setPost);
        commentRepository.save(comment);
    }

    public void delete_comment(Long id, Comment comment) {
        postRepository.findById(id).ifPresent(post -> post.getComments().remove(comment));
        commentRepository.save(comment);
    }


}
