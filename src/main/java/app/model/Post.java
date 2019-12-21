package app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "post_entity")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p_id")
    private final Long id;
    @Column
    @NotNull
    @Size(min=1, message = "Please, add Caption.")
    private  String description;
    @Column
    @NotBlank
    private  String image_url;

    @Column
    private String location;

    @Column
    @CreatedDate
    private Date createdDate;

    @JsonIgnore
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "r_post_user",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "p_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "u_id")
    )
    private User user;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "r_like_post_user",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "p_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "u_id")
    )
    private List<User> likes;

    @JsonProperty("like_counter")
    private int number_likes() {
        return Optional.of(likes).map(List::size).orElse(0);
    }


    @JsonProperty("comments_counter")
    private int comments_counter() {
        return Optional.of(comments).map(List::size).orElse(0);
    }

}
