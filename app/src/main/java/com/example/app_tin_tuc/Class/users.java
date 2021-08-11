package com.example.app_tin_tuc.Class;

public class users {
    private int id_user;
    private String username;
    private String password;
    private String name;
    private int sdt;
    private String email;

    public users(int id_user, String username, String password, String name, int sdt, String email) {
        this.id_user = id_user;
        this.username = username;
        this.password = password;
        this.name = name;
        this.sdt = sdt;
        this.email = email;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSdt() {
        return sdt;
    }

    public void setSdt(int sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
