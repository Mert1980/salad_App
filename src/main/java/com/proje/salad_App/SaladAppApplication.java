package com.proje.salad_App;

import com.proje.salad_App.entity.enums.RoleType;
import com.proje.salad_App.payload.request.AdminRequest;
import com.proje.salad_App.service.AdminService;
import com.proje.salad_App.service.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class SaladAppApplication implements CommandLineRunner {
	private final UserRoleService userRoleService;
	private final AdminService adminService;

	public SaladAppApplication(UserRoleService userRoleService, AdminService adminService) {
		this.userRoleService = userRoleService;
		this.adminService = adminService;
	}

	public static void main(String[] args) {
		SpringApplication.run(SaladAppApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// !!! Rol tablosu doldurulacak
		if (userRoleService.getAllUserRole().size()==0){
			userRoleService.save(RoleType.ADMIN);
			userRoleService.save(RoleType.USER);

		}

		// !!! Admin olusturulacak built in olacak sekilde
		if (adminService.countAllAdmin()==0){
			AdminRequest admin=new AdminRequest();
			admin.setUsername("Admin");
			admin.setPassword("12345678");
			admin.setFirstName("Admin");
			admin.setLastName("Admin");
			admin.setEmail("admin@gmail.com");
			admin.setPhoneNumber("555-444-4321");
			adminService.save(admin);

		}

	}
}
