package app.service;

import app.dao.PostRepository;
import app.model.Post;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }


    public Optional<Post> get_one(Long id) {
        return postRepository.findById(id);
    }

    public Iterable<Post> get_all() {
        return postRepository.findAll();
    }

    public Post create_one(Post post) {
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

}
