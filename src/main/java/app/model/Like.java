package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Entity
@Table(name = "like_entity")
public class Like {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long id;
    @Column
    private final Long user_id;
    @Column
    private final Date date;
    @Column
    private final Long post_id;

}
