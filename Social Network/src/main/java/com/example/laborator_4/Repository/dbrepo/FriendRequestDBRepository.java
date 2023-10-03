package com.example.laborator_4.Repository.dbrepo;


import com.example.laborator_4.Domain.FriendRequest;
import com.example.laborator_4.Domain.Tuple;
import com.example.laborator_4.Domain.Validators.Validator;
import com.example.laborator_4.Repository.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import java.sql.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class FriendRequestDBRepository implements Repository<Tuple<Long, Long>, FriendRequest> {
    private String url;
    private String username;
    private String password;
    private Validator<FriendRequest> validator;

    public FriendRequestDBRepository(String url, String username, String password, Validator<FriendRequest> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    @Override
    public Optional<FriendRequest> findOne(Tuple<Long, Long> longLongTuple) {
        String sql = "SELECT * FROM Friend_Requests WHERE id_sender=? AND id_receiver =?";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, longLongTuple.getLeft());
            statement.setLong(2, longLongTuple.getRight());

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    Date d = resultSet.getDate("request_date");
                    LocalDateTime date = Instant.ofEpochMilli( d.getTime() )
                            .atZone( ZoneId.systemDefault() )
                            .toLocalDateTime();
                    FriendRequest friendRequest = new FriendRequest();
                    friendRequest.seDate(date);
                    friendRequest.setId(longLongTuple);
                    return Optional.of(friendRequest);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Iterable<FriendRequest> findAll() {
        return null;
    }

    @Override
    public Optional<FriendRequest> save(FriendRequest entity) {
        String sql = "INSERT INTO Friend_Requests (id_sender, id_receiver, request_date) VALUES (?, ?, CURRENT_TIMESTAMP)";
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
    public Optional<FriendRequest> delete(Tuple<Long, Long> longLongTuple) {
        String sql = "DELETE FROM Friend_Requests WHERE id_sender=? AND id_receiver=?;";
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setLong(1, longLongTuple.getLeft());
            ps.setLong(2, longLongTuple.getRight());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    @Override
    public Optional<FriendRequest> update(FriendRequest entity) {
        return Optional.empty();
    }

    @Override
    public Iterable<FriendRequest> findFriendsList(Long aLong) {
        return null;
    }

    @Override
    public Iterable<FriendRequest> findFriendsRequests(Long aLong) {
        return null;
    }

    @Override
    public Optional<FriendRequest> findOneByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Iterable<FriendRequest> findAllByName(String FirstName, String LastName) {
        return null;
    }
}
