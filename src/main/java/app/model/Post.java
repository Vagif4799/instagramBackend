package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

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
    private final Long user_id;
    @Column
    @NotNull
    @Size(min=5, message = "Please, add Caption.")
    private  String description;
    @Column
    @NotBlank
    private  String image_url;
    @Column
    private int like_counter;
    @Column
    private String location;
//    @Column
//    private int commnets_counter;
    @Column
    @CreatedDate
    private Date createdDate;


    @ManyToMany(mappedBy = "posts")
    private Set<Comment> comments;


}
