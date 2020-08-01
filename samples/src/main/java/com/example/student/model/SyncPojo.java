package com.example.student.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SyncPojo {
    @SerializedName("data")
    private List<User> user = new ArrayList<User>();
    /**
     *
     * @return The todos
     */
    public List<User> getUser() {
        return user;
    }
    /**
     *
     * @param user
     * The todos
     */
    public void setUser(List<User> user) {
        this.user = user;
    }
}
