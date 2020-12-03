package fr.ul.miage.clickandcollect.core;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import fr.ul.miage.clickandcollect.users.UserRepository;
import lombok.RequiredArgsConstructor;

import static org.springframework.security.core.userdetails.User.withUsername;

@RequiredArgsConstructor
public class DBUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			fr.ul.miage.clickandcollect.users.User user = userRepository.findByUsername(username);
			
			return withUsername(user.getUsername())
					.password(user.getPassword())
					.roles(user.getRole())
					.build();
			
			
	}

}
