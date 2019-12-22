package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
@Entity
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p_id")
    private final Long id;
    String content;
}
