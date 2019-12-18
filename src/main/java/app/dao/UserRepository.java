package app.dao;

import app.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
   // Collection<User> findAllByName(String name);
}
