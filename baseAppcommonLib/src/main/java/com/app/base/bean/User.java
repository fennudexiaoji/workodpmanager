package com.app.base.bean;



public class User {
    public long id;
    public String firstname;
    public String lastname;
    public boolean isFollowing;

    public User() {
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isFollowing=" + isFollowing +
                '}';
    }
}
