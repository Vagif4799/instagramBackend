package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "user_entity")
    public class User {

    @Column(name = "u_id")
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private final Long id;
    @Column
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private  String name;
    @Column
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private   String surname;
    @Column(unique = true)
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private   String username;
    @Column
    @NotNull
    @Size(min = 1, message = "This field can't be empty.")
    private   String mail;
    @Column
    @NotBlank(message = "Password is required")
    private   String password;
    @Column
    private   String gender;
    @Column
    private  Date birthdate;
    @Column
    @NotBlank(message = "Phone number is required")
    private   String phone_number;
    @Column
    private  String description;
    @Column
    private  String profile_photo;
    @Column
    private String cover_photo;
    @Column
    private  int number_followers;
    @Column
    private  int number_follow;

    @OneToMany (mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Post> posts;



}
