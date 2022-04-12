package wf.garnier.devoxx;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

public class RobotAuthentication implements Authentication {

	private final List<SimpleGrantedAuthority> authorities;
	private final boolean authenticated;
	private final String password;

	private RobotAuthentication(List<SimpleGrantedAuthority> authorities, String password) {
		this.authorities = Collections.unmodifiableList(authorities);
		this.authenticated = !CollectionUtils.isEmpty(authorities);
		this.password = password;
	}

	public static RobotAuthentication authenticated() {
		return new RobotAuthentication(List.of(new SimpleGrantedAuthority("ROLE_robot")), null);
	}

	public static RobotAuthentication authenticationToken(String password) {
		return new RobotAuthentication(Collections.emptyList(), password);
	}

	@Override
	public Object getPrincipal() {
		return "Mr Robot";
	}

	@Override
	public String getName() {
		return "Mr Robot";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}


	@Override
	public String getCredentials() {
		return password;
	}

	@Override
	public Object getDetails() {
		return null;
	}


	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		throw new RuntimeException("yeah don't do this please 💣");
	}

}
