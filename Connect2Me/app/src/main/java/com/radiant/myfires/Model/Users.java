package com.radiant.myfires.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sakthivel on 29/12/2016.
 */

public class Users implements Serializable {

    private String id;
    private String userName;
    private String email;
    private String displayName;
    private String dob;
    private boolean isOnline;
    private ArrayList<String> groups;

    public Users(String id, String userName, String email, String displayName, String dob, boolean isOnline, ArrayList<String> groups) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.displayName = displayName;
        this.dob = dob;
        this.isOnline = isOnline;
        this.groups = groups;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }
}
