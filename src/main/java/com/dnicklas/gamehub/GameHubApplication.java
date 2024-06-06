package com.dnicklas.gamehub;

import com.dnicklas.gamehub.account.AccountService;
import com.dnicklas.gamehub.account.User;
import com.dnicklas.gamehub.account.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GameHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameHubApplication.class, args);
	}

}
