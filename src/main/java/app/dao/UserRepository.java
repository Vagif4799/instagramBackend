package app.dao;

import app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsernameAndPassword(@NotNull @Size(min = 1, message = "This field can't be empty.") String username, @NotBlank(message = "Password is required") String password);
    Optional<User> findByUsername(@NotNull @Size(min = 1, message = "This field can't be empty.") String username);
    Optional<List<User>> findByUsernameContainingIgnoreCase(String text);//
}
