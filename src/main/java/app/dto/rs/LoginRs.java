package app.dto.rs;

import app.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;

@AllArgsConstructor
@Getter

public class LoginRs {
    private String message;
    private String token;
    private User user;
}
