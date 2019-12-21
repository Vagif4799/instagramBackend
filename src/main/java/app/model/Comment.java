package app.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "comment_entity")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "c_id")
    private final Long id;
    @Column
    private String comment_itself;


    @JsonIgnore
    @ManyToOne
    @JoinTable(name = "r_comment_post",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id", referencedColumnName = "p_id")
    )
    private Post post;

    @ManyToOne
    @JoinTable(name = "r_comment_user",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "c_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private User commenter;
}







