package app.security.ud;


import app.dao.UserRepository;
import app.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Log4j2
@Configuration
public class UserDetailsServiceJPA implements UserDetailsService {

  private final UserRepository userRepository;

  public UserDetailsServiceJPA(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public static UserDetails mapper(User user) {
    return new MyUserDetails(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            user.getRoles()
    );
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
            .map(UserDetailsServiceJPA::mapper)
            .orElseThrow(()->new UsernameNotFoundException(
                    String.format("User `%s` not found", username)));
  }

  public UserDetails loadUserById(long user_id) throws UsernameNotFoundException {
    return userRepository.findById(user_id)
            .map(UserDetailsServiceJPA::mapper)
            .orElseThrow(()->new UsernameNotFoundException(
                    String.format("User with id:%d` not found", user_id)));
  }
}
