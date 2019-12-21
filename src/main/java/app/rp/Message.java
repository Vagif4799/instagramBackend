package app.rp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
public class Message {
    String message;

    public Message(String message) {
        this.message = message;
    }
}
