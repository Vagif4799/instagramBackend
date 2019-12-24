package app.dto.rq;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data

public class LoginRq {
  private String username;
  private String password;
  private boolean remember;
}
