package instagram.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Data
public class Person {

    private final UUID id;
    @NotBlank
    private final String name;
    @NotBlank
    private final String surname;
    private final String username;
    private final String mail;
    private final String password;
    private final String gender;
    private final String birthdate;
    private final String phone_number;
    private final String description;
    private final String profile_photo;
    private final int number_followers;
    private final int number_follow;

}
