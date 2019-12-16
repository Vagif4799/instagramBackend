package instagram.dao;

import instagram.model.User;

import java.util.List;


public interface UserDAO {

    List<User> get();

    User get(int id);

    void save(User user);

    void delete(int id);

}

