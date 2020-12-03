package fr.ul.miage.clickandcollect.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static org.springframework.http.HttpMethod.OPTIONS;

import java.util.Properties;

import static org.springframework.http.HttpMethod.GET;

import com.sun.istack.NotNull;

import fr.ul.miage.clickandcollect.users.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final SecurityProperties properties;
	
	private final UserRepository userRepository;
	
	private final UserDetailsServiceFactory factory;
	
	@Override
	protected void configure(@NotNull HttpSecurity http) throws Exception{
		http
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/actuator/info").permitAll()
				.antMatchers("/actuator/health").permitAll()
				//.antMatchers("/api/v1/products").permitAll()
			.anyRequest()
			.authenticated();
		
		if(properties.getStoreType()==StoreType.IN_DB) {
			http.userDetailsService(factory.getInDb(userRepository));
		} else {
			http.userDetailsService(factory.getInMemory());
		}
		
		http.httpBasic();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring()
			.antMatchers(OPTIONS, "/**")
			.antMatchers(GET, "/favicon.ico");
	}
	
	@Bean
	@Override
	public UserDetailsService userDetailsService() {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		
		UserDetails user = User.withUsername("user")
				.password(encoder.encode("password"))
				.roles("USER")
				.build();
		
		UserDetails admin = User.withUsername("admin")
				.password(encoder.encode("password"))
				.roles("ADMIN")
				.build();
		
		return new InMemoryUserDetailsManager(user,admin);
	}
	

}
