package app.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE, force=true)
@Entity
@Table(name = "user_entity")
public class User implements UserDetails {

    @Column
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

    // number of followr/eds can be in follower table


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority("Role_User"));
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }
}
