package com.myBlog.api.domain.account;

public enum Roles {
    USER("user"),
    ADMIN("admin");

    private String role;

    Roles(String role){
        this.role= role;
    }

    public String getRole(){
        return role;
    }
}

