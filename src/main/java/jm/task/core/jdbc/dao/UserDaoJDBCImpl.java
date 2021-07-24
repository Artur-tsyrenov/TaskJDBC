package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    Util util = new Util();
    Connection connection = util.getConnection();

    public UserDaoJDBCImpl() {
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {

            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void createUsersTable() {

        try (Statement statement = connection.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS User (id INT(11) " +
                    "NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                    "name VARCHAR(30), lastName VARCHAR(30), age TINYINT(4))");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT User SET NAME=?, LASTNAME=?, AGE=?")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removeUserById(long id) {

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM users WHERE ID=?")) {

            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listOfUsers = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String lastName = resultSet.getString("lastName");
                byte age = resultSet.getByte(4);

                User user = new User(name, lastName, age);
                listOfUsers.add(user);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return listOfUsers;
    }

    public void cleanUsersTable() {

        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM users")) {
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}





