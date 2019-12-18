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

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "post_entity")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    private final Long user_id;
    @NotNull
    @Size(min=5, message = "Please, add Caption.")
    private  String description;
    @NotBlank
    private  String image_url;
    private int like_counter;
    private String location;
    private int commnets_counter;
    @CreatedDate
    private Date createdDate;

}
