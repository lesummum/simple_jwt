package com.simplejwt.demo.dtos;

public class PermissionDto {

    // this permissionPubId is for identify the permission in client side
    // get, update, delete
    private String permissionPubId;

    private String name;

    public String getPermissionPubId() {
        return permissionPubId;
    }

    public void setPermissionPubId(String permissionPubId) {
        this.permissionPubId = permissionPubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
