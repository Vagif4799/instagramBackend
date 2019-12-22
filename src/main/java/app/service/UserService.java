package app.service;

import app.beans.NullAwareBeanUtilsBean;
import app.dao.UserRepository;
import app.model.Post;
import app.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;


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
                    NullAwareBeanUtilsBean bean = new NullAwareBeanUtilsBean();
                    try {
                        bean.copyProperties(u,user);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    userRepository.save(u);
                });
        return user;
    }

    public void del_one(Long id) {
        userRepository.deleteById(id);
    }

    public List<Post> getPostsByUser(Long id) {
         return userRepository.findById(id)
                 .map(User::getPosts)
                 .map(list -> list.stream()
                 .sorted(Comparator.comparingLong(Post::getId).reversed())
                 .collect(Collectors.toList()))
                 .get();
    }

    public List<User> getFollowsByUser_id(Long id) {
        return userRepository.findById(id).map(User::getFollowing).get();
    }

    public List<User> getFollowersByUser_id(Long id) {
        return  userRepository.findById(id).map(User::getFollowers).get();
    }

    public void add_follow(Long id, User user) {
        userRepository.findById(user.getId()).ifPresent(
                followed->
                userRepository.findById(id).ifPresent(follower->{
                    followed.getFollowers().add(follower);
                    userRepository.save(followed);
                })
        );

    }

    public void del_follow(Long id, User user) {
        userRepository.findById(id).ifPresent(u->{
            u.getFollowing().remove(user);
            userRepository.save(u);
        });
    }

    public List<Post> getFeed(Long id) {
         return userRepository.findById(id) // Optional<User>
                .map(User::getFollowing) // Optional<List<User>>
                .map(list -> list.stream()
                            .flatMap(user -> user.getPosts().stream())
                            .sorted(Comparator.comparingLong(Post::getId).reversed())
                            .collect(Collectors.toList())
                ).get();
    }

    public User validate(User user){
        return userRepository.getByUsernameAndPassword(user.getUsername(), user.getPassword()).get();
    }

    public User searchByUserName(String username) {
        return userRepository.getByUsername(username).get();
    }
}
