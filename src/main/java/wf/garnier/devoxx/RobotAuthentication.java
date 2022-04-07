package wf.garnier.devoxx;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class RobotAuthentication implements Authentication {

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
		return List.of(new SimpleGrantedAuthority("ROLE_robot"));
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}


	@Override
	public Object getCredentials() {
		return null;
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
