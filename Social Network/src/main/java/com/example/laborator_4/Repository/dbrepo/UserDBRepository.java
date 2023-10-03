package com.example.laborator_4.Repository.dbrepo;


import com.example.laborator_4.Domain.User;
import com.example.laborator_4.Domain.Validators.Validator;
import com.example.laborator_4.Repository.Repository;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import java.util.Optional;

public class UserDBRepository implements Repository<Long, User> {
    private String url;
    private String username;
    private String password;
    private Validator<User> validator;

    public UserDBRepository(String url, String username, String password, Validator<User> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Optional<User> findOne(Long aLong) {
        String sql = "SELECT * FROM Users WHERE id=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    User user = new User(firstName, lastName);
                    user.setId(id);
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<User> findAll() {
        Set<User> users = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from users");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");

                User utilizator = new User(firstName, lastName);
                utilizator.setId(id);
                users.add(utilizator);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> save(User entity) {
        if(findOneByEmail(entity.getEmail()).isEmpty()){
            String sql = "insert into users (first_name, last_name, password, email) values (?, ?, ?, ?)";
            validator.validate(entity);
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setString(1, entity.getFirstName());
                ps.setString(2, entity.getLastName());
                ps.setString(3, entity.getPassword());
                ps.setString(4, entity.getEmail());

                ps.executeUpdate();
            } catch (SQLException e) {
                //e.printStackTrace();
                return Optional.ofNullable(entity);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Long aLong) {
        String sql = "DELETE FROM Users WHERE id=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, aLong);

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> update(User entity) {
        String sql = "UPDATE Users SET first_name=?, last_name=? WHERE id=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setString(1, entity.getFirstName());
            ps.setString(2, entity.getLastName());
            ps.setLong(3, entity.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<User> findFriendsList(Long aLong){
        Set<User> users = new HashSet<>();
        String sql = "SELECT * FROM Users INNER JOIN Friendships ON Friendships.id1 = ? AND Users.id = Friendships.id2 OR Friendships.id2 = ? AND Users.id = Friendships.id1;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);
            statement.setLong(2, aLong);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    User user = new User(firstName, lastName);
                    user.setId(id);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Iterable<User> findFriendsRequests(Long aLong){
        Set<User> users = new HashSet<>();
        String sql = "SELECT * FROM Users INNER JOIN Friend_Requests ON Friend_Requests.id_sender = Users.id AND Friend_Requests.id_receiver=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, aLong);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    User user = new User(firstName, lastName);
                    user.setId(id);
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Optional<User> findOneByEmail(String email){
        String sql = "SELECT * FROM Users WHERE email=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String pasword = resultSet.getString("password");

                    User user = new User(firstName, lastName, email, pasword);
                    user.setId(id);
                    return Optional.of(user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Iterable<User> findAllByName(String FirstName, String LastName){
        Set<User> users = new HashSet<>();
        String sql = "SELECT * FROM Users WHERE first_name=? AND last_name=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, FirstName);
            statement.setString(2, LastName);

            try(ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");

                    User user = new User(firstName, lastName);
                    user.setId(id);
                    users.add(user);
                }
                return users;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}