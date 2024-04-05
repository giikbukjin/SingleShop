package com.elice.team4.singleShop;

import com.elice.team4.singleShop.user.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SingleShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingleShopApplication.class, args);
	}

	@Bean
	@Profile(value = {"local", "test", "default"})
	public DataInit stubDataInit(UserRepository userRepository) {
		return new DataInit(userRepository);
	}

}
