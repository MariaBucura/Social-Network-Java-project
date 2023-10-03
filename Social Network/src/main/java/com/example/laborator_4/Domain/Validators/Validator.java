package com.example.laborator_4.Domain.Validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}