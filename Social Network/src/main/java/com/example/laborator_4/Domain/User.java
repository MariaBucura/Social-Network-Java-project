package com.example.laborator_4.Domain;

import java.util.List;
import java.util.Objects;

public class User extends Entity<Long>{
    protected String firstName;
    protected String lastName;

    private String password;

    private String email;
    private List<User> friends;

    private List<User> friendRequests;

    public User(){}
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword(){return password;}

    public String getEmail(){return email;}

    public void setPassword(String password){this.password = password;}

    public void setEmail(String email){this.email = email;}

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends){this.friends = friends;}

    @Override
    public String toString() {
        return "Utilizator{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", friends=" + friends + '\'' +
                ", friend requests= " + friendRequests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName(), getFriends());
    }
}
