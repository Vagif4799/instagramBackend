package instagram.dao;

import instagram.model.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;


public class UserDAOImpl implements UserDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<User> get() {
        Session currentSession = entityManager.unwrap(Session.class);
        Query<User> query = currentSession.createQuery("from users", User.class);
        List<User> list = query.getResultList();
        return list;
    }

    @Override
    public User get(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        User userObj = currentSession.get(User.class, id);
        return userObj;
    }

    @Override
    public void save(User user) {
        Session currentSession = entityManager.unwrap(Session.class);
        currentSession.saveOrUpdate(user);
    }

    @Override
    public void delete(int id) {
        Session currentSession = entityManager.unwrap(Session.class);
        User userObj = currentSession.get(User.class, id);
        currentSession.delete(userObj);
    }
}
