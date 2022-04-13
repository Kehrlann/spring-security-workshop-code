package wf.garnier.devoxx;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class RobotAccountConfigurer extends AbstractHttpConfigurer<RobotAccountConfigurer, HttpSecurity> {
	private final Set<String> passwords = new HashSet<>();

	@Override
	public void init(HttpSecurity http) {
		// Called when http.build() is called.
		// All Configurers have their .init() called first.
		// Typically used to setup some defaults and register AuthenticationProviders.
		// No-op by default.
		var passwords = this.passwords.toArray(new String[]{});
		http.authenticationProvider(new RobotAuthenticationProvider(passwords));
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// Called when http.build() is called.
		// All Configurers have their .configure() called after ALL configurers had .init() called.
		// Here we have the most objects in the http.getSharedObject cache.
		// Other Configurers have been added and init'd, too.
		//
		// Typically: register Filters.
		var authManager = http.getSharedObject(AuthenticationManager.class);
		http.addFilterBefore(new RobotAccountFilter(authManager), UsernamePasswordAuthenticationFilter.class);
	}

	public RobotAccountConfigurer password(String password) {
		System.out.println(password);
		this.passwords.add(password);
		return this;
	}
}
