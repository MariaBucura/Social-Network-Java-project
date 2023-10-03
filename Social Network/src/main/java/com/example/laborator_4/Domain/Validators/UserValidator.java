package com.example.laborator_4.Domain.Validators;

import com.example.laborator_4.Domain.User;
public class UserValidator implements Validator<User> {
    @Override
    public void validate(User entity) throws ValidationException {
        //TODO: implement method validate
        if(entity.getFirstName() == null || entity.getLastName() == null)
            throw new ValidationException("Names cannot be null");
    }
}
