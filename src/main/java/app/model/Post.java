package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "post_entity")
public class Post {

    private final int id;
    private final int user_id;
    private  String description;
    private  String image_url;
    private int like_counter;
    private String location;
    private int commnets_counter;
    @CreatedDate
    private Date createdDate;

}
