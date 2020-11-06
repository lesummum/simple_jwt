package com.simplejwt.demo.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String rolePubId;

    @Column(nullable = false)
    private String name;

    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true
    )
    private Set<UserEntity> Users = new HashSet<UserEntity>();

    public Set<UserEntity> getUsers() {
        return Users;
    }

    public void setUsers(Set<UserEntity> users) {
        Users = users;
    }

    @ManyToMany(fetch = FetchType.LAZY
            //cascade = CascadeType.PERSIST
            )
	@JoinTable(
	  name = "roles_permissions",
	  joinColumns =
	          @JoinColumn(name = "role_id")
              ,
	  inverseJoinColumns =
	          @JoinColumn(name = "permission_id")
	                )
    private Set<PermissionEntity> permissions = new HashSet<PermissionEntity>();

    public Set<PermissionEntity> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionEntity> permissions) {
        this.permissions = permissions;
    }

//    public void addPermission(PermissionEntity permission) {
//        this.permissions.add(permission);
//        permission.getRoles().add(this);
//    }

//    public void removePermission(PermissionEntity permission) {
//        this.permissions.remove(permission);
//        permission.getRoles().remove(this);
//    }

    //  protected List<PermissionEntity> permissions = new ArrayList<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
