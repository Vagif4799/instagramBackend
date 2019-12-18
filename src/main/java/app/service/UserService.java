package app.service;

import app.dao.UserRepository;
import app.model.User;
import org.springframework.stereotype.Service;
import java.util.Optional;


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

    public void del_one(Long id) {
        userRepository.deleteById(id);
    }

    public User save_changes(User user) {
        userRepository.save(user);
        return user;
    }




}
