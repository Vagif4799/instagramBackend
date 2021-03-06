package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "user_entity")
    public class User {

    @Column(name = "u_id")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private final Long id;

    @Column
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private  String name;

    @Column(unique = true)
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private  String username;

    @Column
    private String roles;

    @Transient
    @JsonIgnore
    private final String ROLES_DELIMITER = ":";

    @JsonIgnore
    public String[] getRoles() {
        if (this.roles == null || this.roles.isEmpty()) return new String[]{};
        return this.roles.split(ROLES_DELIMITER);
    }

    public void setRoles(String[] roles) {
        this.roles = String.join(ROLES_DELIMITER, roles);
    }

    @Column
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private   String mail;

    @Column
    @NotBlank(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private   String password;

    @Column
    private   String gender;
    @Column
    private  Date birthdate;
    @Column
    private   String phone_number;
    @Column
    private  String description;
    @Column(columnDefinition="varchar(1000)")
    private  String profile_photo;
    @Column(columnDefinition="varchar(1000)")
    private  String cover_photo;

    @JsonIgnore
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "r_followers",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id", referencedColumnName = "u_id")
    )
    private List<User> followers;

    @JsonIgnore
    @ManyToMany(mappedBy = "followers")
    private List<User> following;

    @JsonIgnore
    @OneToMany(mappedBy = "commenter", cascade = CascadeType.ALL, orphanRemoval = true )
    private List<Comment> comments;

    @Transient
    private boolean followed;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public int getNumber_followers() {
        return Optional.ofNullable(followers).map(List::size).orElse(0);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int getNumber_follow() {
        return Optional.ofNullable(following).map(List::size).orElse(0);
    }

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int getCount_posts(){
        return Optional.ofNullable(posts).map(List::size).orElse(0);
    }

    public void setNumber_followers(int number_followers) {
    }

    public void setNumber_follow(int number_follow) {
    }

    public void setCount_posts(int count_posts) {
    }



    public Boolean areFriends(User user){
        return getFollowing().contains(user) && user.getFollowing().contains(this);
    }

    @JsonIgnore
    public List<User> getFriends(){
        return getFollowing()
                .stream()
                .filter(this::areFriends)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFriends(User user){
        return getFriends().stream()
                .filter(user.getFriends()::contains)
                .collect(Collectors.toList());
    }

    public List<User> getCommonFollowing(User user){
        return getFollowing().stream()
                .filter(user.getFollowing()::contains)
                .collect(Collectors.toList());
    }

    public List<User> getFriendsThatFollows(User user){
        return getFriends().stream()
                .filter(f -> f.getFollowing().contains(user))
                .collect(Collectors.toList());
    }

    public List<User> getFollowingThatFollows(User user){
        return getFollowing().stream()
                .filter(f -> f.getFollowing().contains(user))
                .collect(Collectors.toList());
    }

}
