package app.service;

import app.beans.NullAwareBeanUtilsBean;
import app.dao.UserRepository;
import app.model.Post;
import app.model.User;
import app.util.CurrentUser;
import app.util.Pair;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder enc;

    public UserService(UserRepository userRepository, PasswordEncoder enc) {
        this.userRepository = userRepository;
        this.enc = enc;
    }

    public Optional<User> get_one(Long id) {
        return userRepository.findById(id)
        .map(u-> {u.setFollowed(CurrentUser.get().getFollowing().contains(u)); return u;});
    }

    public Iterable<User> get_all() {
        Stream<User> allUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false);
        return allUsers.peek(u-> u.setFollowed(CurrentUser.get().getFollowing().contains(u)))
                .collect(Collectors.toList());

    }

    public User update_user(User user){
        Optional<User> old_user = userRepository.findById(CurrentUser.get().getId());
                old_user.ifPresent(u-> {
                    NullAwareBeanUtilsBean bean = new NullAwareBeanUtilsBean();
                    try {
                        bean.copyProperties(u,user);
                        if(user.getPassword() != null) u.setPassword(enc.encode(user.getPassword()));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    userRepository.save(u);
                });
        return get_one(CurrentUser.get().getId()).get();
    }

    public void del_one() {
        userRepository.deleteById(CurrentUser.get().getId());
    }

    public List<Post> getPostsByUser(Long id) {
         return userRepository.findById(id)
                 .map(User::getPosts)
                 .map(list -> list.stream()
                 .peek(p->p.setLiked(p.getLikes().contains(CurrentUser.get())))
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

    public void add_follow(User user) {
        userRepository.findById(user.getId()).ifPresent(
                followed-> {
                    followed.getFollowers().add(CurrentUser.get());
                    userRepository.save(followed);
                }
        );

    }

    public void del_follow(User user) {
        userRepository.findById(user.getId()).ifPresent(
                followed-> {
                    followed.getFollowers().remove(CurrentUser.get());
                    userRepository.save(followed);
                }
        );
    }

    public List<Post> getFeed() {
         return userRepository.findById(CurrentUser.get().getId()) // Optional<User>
                .map(User::getFollowing) // Optional<List<User>>
                .map(list -> list.stream()
                            .flatMap(user -> user.getPosts().stream())
                            .peek(p->p.setLiked(p.getLikes().contains(CurrentUser.get())))
                            .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                            .collect(Collectors.toList())
                ).get();
    }

    public List<User> search(String search_content) {
        return userRepository.findByUsernameContainingIgnoreCase(search_content)
                .map(list->list.stream()
                        .peek(u-> u.setFollowed(CurrentUser.get().getFollowing().contains(u)))
                        .sorted(Comparator.comparingLong(u->{
                            String username = u.getUsername().toLowerCase();
                            return username.indexOf(search_content.toLowerCase());
                        }))
                        .collect(Collectors.toList()))
                .get();
    }

    public List<User> getRecommended(){

        Stream<User> allUsers = StreamSupport.stream(userRepository.findAll().spliterator(), false);
        User user = CurrentUser.get();

        return allUsers
                .peek(u-> u.setFollowed(user.getFollowing().contains(u)))
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
