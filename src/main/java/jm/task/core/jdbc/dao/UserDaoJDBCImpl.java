package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    private final Connection connection = Util.getMySQLConnection();

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE Users (id BIGINT(255) NOT NULL AUTO_INCREMENT, " +
                "name CHAR(60), lastName CHAR(60), age INT NOT NULL, PRIMARY KEY (id));";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("При создании таблицы произошло исключение.\n" + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String sqlCommand = "DROP TABLE Users;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("При удалении таблицы произошло исключение.\n" + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand = "INSERT INTO users (name, lastName, age) VALUES(?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("При добавлении user(s) произошло исключение.\n" + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sqlCommand = "DELETE FROM Users WHERE id = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("При удалении user по id произошло исключение.\n" + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sqlCommand = "SELECT * FROM Users";
        List<User> usersArray = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                usersArray.add(user);
            }

        } catch (SQLException e) {
            System.err.println("При получении списка users произошло исключение.\n" + e.getMessage());
        }
        return usersArray;
    }

    public void cleanUsersTable() {
        String sqlCommand = "DELETE FROM Users";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("При очистке таблицы users произошло исключение.\n" + e.getMessage());
        }
    }
}
