package com.simplejwt.demo.dtos;

public class RoleDto {

    // this rolePubId is for identify the role in client side
    // get, update, delete
    private String rolePubId;

    private String name;

    public String getRolePubId() {
        return rolePubId;
    }

    public void setRolePubId(String rolePubId) {
        this.rolePubId = rolePubId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
