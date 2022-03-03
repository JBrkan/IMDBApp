package com.imdbapp.datamodels;

import com.imdbapp.datamodels.databasemodel.Users;

import java.util.List;

public class UserWrapper {
    private List<Users> users;

    public UserWrapper(List<Users> users) {
        this.users = users;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
