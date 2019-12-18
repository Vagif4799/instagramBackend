package app.model;


import com.sun.javafx.beans.IDProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "comment_entity")
public class Comment {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @Column
    private final Long post_id;
    @Column
    private String comment_itself;
    @Column
    private final Long user_id;

}

