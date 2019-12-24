package app.service;

import app.dao.CommentRepository;
import app.dao.PostRepository;
import app.model.Comment;
import app.model.Post;
import app.model.User;
import app.util.CurrentUser;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    public Optional<Post> get_one(Long id) {
        return postRepository.findById(id)
                .map(p->{p.setLiked(p.getLikes().contains(CurrentUser.get())); return p;});
    }

    public Iterable<Post> get_all() {
        Stream<Post> allPosts = StreamSupport.stream(postRepository.findAll().spliterator(), false);
        return allPosts.peek(p-> p.setLiked(p.getLikes().contains(CurrentUser.get())))
                .collect(Collectors.toList());
    }

    public void create_one(Post post) {
        post.setUser(CurrentUser.get());
        postRepository.save(post);
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

    public void add_like( Long post_id) {
        postRepository.findById(post_id).ifPresent(post -> {
            post.getLikes().add(CurrentUser.get());
            postRepository.save(post);
        });
    }

    public void delete_like(Long id) {
        postRepository.findById(id).ifPresent(post->{
            post.getLikes().remove(CurrentUser.get());
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
        comment.setCommenter(CurrentUser.get());
        postRepository.findById(post_id).ifPresent(comment::setPost);
        commentRepository.save(comment);
    }

    public void delete_comment(Long id, Comment comment) {
        postRepository.findById(id).ifPresent(post -> post.getComments().remove(comment));
        commentRepository.save(comment);
    }


}
