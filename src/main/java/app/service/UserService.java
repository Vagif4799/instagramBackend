package app.service;

import app.beans.NullAwareBeanUtilsBean;
import app.dao.UserRepository;
import app.model.Post;
import app.model.User;
import app.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


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
        return get_one(id).get();
    }

    public void del_one(Long id) {
        userRepository.deleteById(id);
    }

    public List<Post> getPostsByUser(Long id) {
         return userRepository.findById(id)
                 .map(User::getPosts)
                 .map(list -> list.stream()
                 .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
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
                            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                            .collect(Collectors.toList())
                ).get();
    }

    public User validate(User user){
        return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword()).get();
    }

    public List<User> search(String search_content) {
        return userRepository.findByUsernameContainingIgnoreCase(search_content)
                .map(list->list.stream()
                        .sorted(Comparator.comparingLong(u->{
                            String username = u.getUsername().toLowerCase();
                            return username.indexOf(search_content.toLowerCase());
                        }))
                        .collect(Collectors.toList()))
                .get();
    }

    public List<User> getRecommended(Long id){

        Stream<User> allUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false);
        User user = userRepository.findById(id).get();

        return allUsers
                .filter(user1 -> !user.equals(user1))
                .filter(user1->!user.getFollowing().contains(user1))
                .map(user1 -> new Pair<User, Long>(user1, 0L))
                .peek(pair-> pair.second += user.getCommonFriends(pair.first).size()*2)
                .peek(pair-> pair.second += user.getFriendsThatFollows(pair.first).size())
                .peek(pair-> pair.second += user.getCommonFollowing(pair.first).size())
                .peek(pair-> pair.second += user.getFollowingThatFollows(pair.first).size())
                .peek(pair-> pair.second += user.getFollowers().contains(pair.first)?3:0)
                .sorted(Comparator.comparingLong(Pair<User, Long>::getSecond).reversed())
                .map(Pair::getFirst)
                .limit(10)
                .collect(Collectors.toList());

    }


}
