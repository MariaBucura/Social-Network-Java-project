package com.example.laborator_4.Domain.Validators;


import com.example.laborator_4.Domain.Friendship;
import com.example.laborator_4.Domain.User;
import com.example.laborator_4.Repository.Repository;
import com.example.laborator_4.Repository.dbrepo.UserDBRepository;

import java.util.Objects;

public class FriendshipValidator implements Validator<Friendship> {

    private Repository<Long, User> dbRepository = new UserDBRepository("jdbc:postgresql://localhost:5432/social_network", "postgres", "IWuvJohn!1", new UserValidator());
    @Override
    public void validate(Friendship entity) throws ValidationException {
        if(Objects.equals(entity.getId().getLeft(), entity.getId().getRight())){
            throw new ValidationException("Ids cannot be the same.");
        }
        if(dbRepository.findOne(entity.getId().getLeft()).isEmpty() || dbRepository.findOne(entity.getId().getRight()).isEmpty()){
            throw new ValidationException("User does not exist.");
        }

    }
}
