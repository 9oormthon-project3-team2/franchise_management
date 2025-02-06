package com.goorm.friendchise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class FriendchiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FriendchiseApplication.class, args);
	}

}
