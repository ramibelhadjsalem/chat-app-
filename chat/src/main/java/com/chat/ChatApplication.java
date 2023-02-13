package com.chat;

import com.chat.models.ERole;
import com.chat.models.Role;
import com.chat.repo.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ChatApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(ChatApplication.class, args);
	}

	@Bean
	CommandLineRunner run(RoleRepo roleRpository ){
		return args -> {
			/*storageService.init();*/
			if (roleRpository.count()<1) {
				roleRpository.save(Role.builder().name(ERole.ROLE_USER).build());
				roleRpository.save(Role.builder().name(ERole.ROLE_PRO).build());
				roleRpository.save(Role.builder().name(ERole.ROLE_ADMIN).build());
			}
		};
	}
	@Configuration
	public class WebConfiguration implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**").allowedMethods("*");
		}
	}

}
