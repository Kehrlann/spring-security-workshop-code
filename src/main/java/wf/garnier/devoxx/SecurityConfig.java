package wf.garnier.devoxx;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		var tempAuthenticationManager = new ProviderManager(new RobotAuthenticationProvider("beep-boop", "boop-beep"));

		return http.authenticationProvider(new DanielAuthenticationProvider())
					.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/error").permitAll()
					.antMatchers("/favicon.ico").permitAll()
					.anyRequest().authenticated()
				.and().httpBasic()
				.and().formLogin()
				.and().oauth2Login()
				.and()
					.addFilterBefore(new RobotAccountFilter(tempAuthenticationManager), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				new User("user", "{noop}password", Collections.emptyList())
		);
	}
}
