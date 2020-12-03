package fr.ul.miage.clickandcollect.core;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.*;

@RequiredArgsConstructor
public class DBAuthenticationProvider implements AuthenticationProvider {
	
	private final UserDetailsService detailsService;
	private final PasswordChecker checker;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(authentication.getCredentials() == null) {
			return null;
		}
		
		final var userDetails = detailsService.loadUserByUsername(authentication.getPrincipal().toString());
		
		//Si les mots de passe correspondent
		if(checker.areEqual(userDetails.getPassword(), authentication.getCredentials().toString())) {
			final var token = (UsernamePasswordAuthenticationToken) authentication;
			
            return new UsernamePasswordAuthenticationToken(
                    token.getPrincipal(),
                    userDetails.getPassword(),
                    userDetails.getAuthorities()
            );
		}
		
		//Renvoie exception si l'authentification echoue
		throw new AuthFailException();
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
