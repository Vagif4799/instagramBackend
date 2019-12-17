package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "user_entity")
public class User {

    @Column
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private final int id;
    @Column
    private  String name;
    @Column
    private  String surname;
    @Column
    @NotNull
    private  String username;
    @Column
    private  String mail;
    @Column
    private  String password;
    @Column
    private  String gender;
    @Column
    private Date birthdate;
    @Column
    private  String phone_number;
    @Column
    private  String description;
    @Column
    private  String profile_photo;
    @Column
    private  int number_followers;
    @Column
    private  int number_follow;






}
