package com.cinek.rozlitch;

import com.cinek.rozlitch.config.CustomUserDetails;
import com.cinek.rozlitch.models.*;
import com.cinek.rozlitch.repositories.MoneyRequestRepository;
import com.cinek.rozlitch.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cinek.rozlitch.services.UserService;

import java.util.Arrays;

@SpringBootApplication
public class RozlitchApplication {

	 @Autowired
	MoneyRequestRepository moneyRequestRepository;


	public static void main(String[] args) {
		SpringApplication.run(RozlitchApplication.class, args);
	}
	@Bean
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Autowired
    private UserService service;

	/**
	 * Password grants are switched on by injecting an AuthenticationManager.
	 * Here, we setup the builder so that the userDetailsService is the one we coded.
	 * @param builder
	 * @param repository
	 * @throws Exception
	 */
	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserRepository repository) throws Exception {

		if (repository.count()==0) {

			User user1 = new User("user", "user", Arrays.asList(new Role("USER"), new Role("ACTUATOR")));
			user1.setBankNumber("997");
			user1.seteMail("daredevil@o2.pl");
			service.save(user1);
			User user2 = new User("user2", "user", Arrays.asList(new Role("USER"), new Role("ACTUATOR")));
			user2.setBankNumber("93434");
			user2.seteMail("poteznyuser@o2.pl");
			MoneyRequest mr = new MoneyRequest();
			mr.setStatus(Status.REQUESTED);
			mr.setCreator(user1);
			mr.setRequestedUser(user2);
			Item item = new Item();
			item.setCategory("wóda");
			item.setName("ZOLADKOWA DELUXE");
			item.setPrice(new Double(24));
			item.setQuantity(2);
			mr.addItem(item);
			service.save(user2);
			moneyRequestRepository.save(mr);

		}
		builder.userDetailsService(userDetailsService(repository)).passwordEncoder(getPasswordEncoder());
	}

	/**s
	 * We return an istance of our CustomUserDetails.
	 * @param repository
	 * @return
	 */
	private UserDetailsService userDetailsService(final UserRepository repository) {
		return username -> new CustomUserDetails(repository.findByUsername(username));
	}

}
