package wf.garnier.devoxx;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class RobotAuthenticationProvider implements AuthenticationProvider {

	private final List<String> passwords;

	public RobotAuthenticationProvider(String... password) {
		this.passwords = Arrays.asList(password);

	}

	@Override
	public Authentication authenticate(Authentication authenticationToken) throws AuthenticationException {
		var authentication = (RobotAuthentication) authenticationToken;
		if (this.passwords.contains(authentication.getCredentials())) {
			return RobotAuthentication.authenticated();
		} else {
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return RobotAuthentication.class.isAssignableFrom(authentication);
	}
}
