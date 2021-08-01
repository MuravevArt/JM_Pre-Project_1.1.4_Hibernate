package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables("mydbtest", null, "Users", null);
            if (tables.next()) {
                System.out.println("Такая таблица уже есть! Новая создана не была.");
            } else {
                statement.execute("CREATE TABLE Users " +
                        " (id BIGINT(255) NOT NULL AUTO_INCREMENT, " +
                        "  name CHAR(60) , " +
                        "  lastName CHAR(60) , " +
                        "  age INT NOT NULL, " +
                        "  PRIMARY KEY (id));");
                System.out.println("Таблица создана.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet tables = dbm.getTables("mydbtest", null, "users", null);
            if (tables.next()) {
                statement.execute("DROP TABLE Users");
                System.out.println("Таблица удалена.");
            } else {
                System.out.println("Такой таблицы еше нет! Удалять нечего.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection conn = Util.getMySQLConnection(); PreparedStatement preparedStatement =
                conn.prepareStatement("INSERT INTO users (name, lastName, age) VALUES(?, ?, ?);")) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {
            statement.execute("DELETE FROM Users WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersArray = new ArrayList<>();
        try (Connection conn = Util.getMySQLConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM Users");
             ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM Users")) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersArray.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersArray;
    }

    public void cleanUsersTable() {
        try (Connection conn = Util.getMySQLConnection(); Statement statement = conn.createStatement()) {
            statement.execute("DELETE FROM Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
