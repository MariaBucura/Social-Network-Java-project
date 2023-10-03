package com.example.laborator_4.Repository.dbrepo;

import com.example.laborator_4.Domain.Friendship;
import com.example.laborator_4.Domain.Tuple;
import com.example.laborator_4.Domain.Validators.Validator;
import com.example.laborator_4.Repository.Repository;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class FriendshipDBRepository implements Repository<Tuple<Long, Long>, Friendship> {

    private String url;
    private String username;
    private String password;
    private Validator<Friendship> validator;

    public FriendshipDBRepository(String url, String username, String password, Validator<Friendship> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> longLongTuple) {
        String sql = "SELECT * FROM Friendships WHERE id1=? AND id2 =? OR id1=? AND id2=?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, longLongTuple.getLeft());
            statement.setLong(2, longLongTuple.getRight());
            statement.setLong(3, longLongTuple.getRight());
            statement.setLong(4, longLongTuple.getLeft());

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Date d = resultSet.getDate("friendship_date");
                    LocalDateTime date = Instant.ofEpochMilli( d.getTime() )
                            .atZone( ZoneId.systemDefault() )
                            .toLocalDateTime();
                    Friendship friendship = new Friendship();
                    friendship.seDate(date);
                    friendship.setId(longLongTuple);
                    return Optional.of(friendship);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAll() {
        return null;
    }

    @Override
    public Optional<Friendship> save(Friendship entity) {
        String sql = "INSERT INTO Friendships (id1, id2, friendship_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
        validator.validate(entity);
        if(findOne(entity.getId()).isEmpty()){
            try (Connection connection = DriverManager.getConnection(url, username, password);
                 PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setLong(1, entity.getId().getLeft());
                ps.setLong(2, entity.getId().getRight());

                ps.executeUpdate();
            } catch (SQLException e) {
                //e.printStackTrace();
                return Optional.ofNullable(entity);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> longLongTuple) {
        String sql = "DELETE FROM Friendships WHERE id1=? AND id2=? OR id1=? AND id2=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, longLongTuple.getLeft());
            ps.setLong(2, longLongTuple.getRight());
            ps.setLong(3, longLongTuple.getRight());
            ps.setLong(4, longLongTuple.getLeft());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<Friendship> update(Friendship entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findFriendsList(Long aLong) {
        return null;
    }

    @Override
    public Iterable<Friendship> findFriendsRequests(Long aLong) {
        return null;
    }

    @Override
    public Optional<Friendship> findOneByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Iterable<Friendship> findAllByName(String FirstName, String LastName) {
        return null;
    }
}
