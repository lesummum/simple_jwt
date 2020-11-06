package com.simplejwt.demo.models;



import java.util.HashSet;
import java.util.Set;


import javax.persistence.*;


@Entity
@Table(name = "permissions")
public class PermissionEntity {
    @Id
    @Column(name = "permission_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String permissionPubId;

    @Column(nullable = false)
    private String name;
    @ManyToMany(mappedBy = "permissions")

    private Set<RoleEntity> roles = new HashSet<RoleEntity>();

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

//    public void addRole(RoleEntity role) {
//        this.roles.add(role);
//        role.getPermissions().add(this);
//    }

//    public void removeRole(RoleEntity role) {
//        this.roles.remove(role);
//        role.getPermissions().remove(this);
//    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
