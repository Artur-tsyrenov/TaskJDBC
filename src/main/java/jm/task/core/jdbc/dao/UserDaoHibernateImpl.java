package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query query = session.createSQLQuery("CREATE TABLE IF NOT EXISTS User (id INT(11)" +
                    "NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(30), lastName VARCHAR(30), age TINYINT(4))").addEntity(User.class);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createSQLQuery("DROP TABLE IF EXISTS user").addEntity(User.class);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            User user = new User();
            user.setId(id);
            session.delete(user);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query<User> query = session.createQuery("from User");
            listOfUsers = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listOfUsers;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.getSessionFactory().openSession()) {
            session.beginTransaction();

            Query<User> query = session.createQuery("delete User");
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
