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
    private   String username;
    @Column
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private   String mail;
    @Column
    @NotBlank(message = "Password is required")
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
    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
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

    @JsonProperty(value = "number_followers") // or whatever name you need in JSON
    private int number_followers() {
        return Optional.of(followers).map(List::size).orElse(0);
    }

    @JsonProperty(value = "number_follow") // or whatever name you need in JSON
    private int number_following() {
        return Optional.of(following).map(List::size).orElse(0);
    }

    @JsonProperty(value = "count_posts")
    private int count_posts(){
        return Optional.of(posts).map(List::size).orElse(0);
    }

}
