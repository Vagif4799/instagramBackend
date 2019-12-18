package app.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private final Long post_id;
    @Column
    private String comment_itself;
    @Column
    private final Long user_id;


    @ManyToMany
    @JoinTable(name = "r_comment_post",
            joinColumns =
                    {
                            @JoinColumn(name = "comment_id", referencedColumnName = "c_id")
                    },
            inverseJoinColumns = {
                            @JoinColumn(name = "post_id", referencedColumnName = "p_id")
            }
    )

    private Set<Post> posts;



}

