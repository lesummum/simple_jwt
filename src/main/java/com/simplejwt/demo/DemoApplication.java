package com.simplejwt.demo;

import com.simplejwt.demo.bll.tools.Utils;
import com.simplejwt.demo.dal.PermissionRepository;
import com.simplejwt.demo.dal.RoleRepository;
import com.simplejwt.demo.dal.SpringApplicationContext;
import com.simplejwt.demo.models.PermissionEntity;
import com.simplejwt.demo.models.RoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;


@SpringBootApplication
public class DemoApplication  implements CommandLineRunner {
	@Autowired
    private RoleRepository roleRepository;
 	@Autowired
    private PermissionRepository permissionRepository;

	@Autowired
    private Utils utils;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringApplicationContext springApplicationContext()
	{
		return new SpringApplicationContext();
	}



	@Override
	public void run(String... args) throws Exception {

		// Permissions /////////////////////////
		// create default Permission in database
		PermissionEntity perRead = new PermissionEntity();
		PermissionEntity perCreate = new PermissionEntity();
		PermissionEntity perUpdate = new PermissionEntity();
		PermissionEntity perDelete = new PermissionEntity();

		perRead.setName("Read");
		perRead.setPermissionPubId(utils.generatePubId(30));


		perCreate.setName("Create");
		perCreate.setPermissionPubId(utils.generatePubId(30));

		perUpdate.setName("Update");
		perUpdate.setPermissionPubId(utils.generatePubId(30));

		perDelete.setName("Delete");
		perDelete.setPermissionPubId(utils.generatePubId(30));

		permissionRepository.saveAll(Arrays.asList(perRead, perCreate,perUpdate,perDelete));


//		permissionRepository.save(perRead);
//		permissionRepository.save(perCreate);
//		permissionRepository.save(perUpdate);
//		permissionRepository.save(perDelete);




		// Roles /////////////////////////

		// Create default Role
		RoleEntity roleAdmin = new RoleEntity();
		RoleEntity roleUser = new RoleEntity();


		roleUser.setName("User");
		roleUser.setRolePubId(utils.generatePubId(30));

		roleAdmin.setName("Admin");
		roleAdmin.setRolePubId(utils.generatePubId(30));


		roleRepository.saveAll(Arrays.asList(roleAdmin, roleUser));


 		// add permission to the role ///////////////////////////
		roleAdmin.getPermissions().addAll(Arrays.asList(perRead, perCreate, perUpdate, perDelete));
		roleUser.getPermissions().addAll(Arrays.asList(perRead, perCreate));

		// update the role ////////////////////////
		roleRepository.save(roleAdmin);
		roleRepository.save(roleUser);



	}
}
