package app.service;

import app.dao.UserRepository;
import app.model.Post;
import app.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Optional<User> get_one(Long id) {
        return userRepository.findById(id);
    }

    public Iterable<User> get_all() {
        return userRepository.findAll();
    }

    public User create_one(User user) {
        userRepository.save(user);
        return user;
    }

    public User update_user(User user, Long id){
        Optional<User> old_user = userRepository.findById(id);
                old_user.ifPresent(u-> {
                    BeanUtils.copyProperties(user, u);
                    userRepository.save(u);
                });
        return user;
    }

    public void del_one(Long id) {
        userRepository.deleteById(id);
    }

    public Set<Post> getPostsByUser(Long id) {
        return userRepository.findById(id).map(u->u.getPosts()).get();
    }

}
