package wf.garnier.devoxx;

import java.util.Collections;

import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.oidc.authentication.OidcAuthorizationCodeAuthenticationProvider;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationEventPublisher publisher) throws Exception {
		http.getSharedObject(AuthenticationManagerBuilder.class).authenticationEventPublisher(publisher);

		// @formatter:off
		return http.authenticationProvider(new DanielAuthenticationProvider())
					.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/error").permitAll()
					.antMatchers("/favicon.ico").permitAll()
					.anyRequest().authenticated()
				.and().httpBasic()
				.and().formLogin()
				.and().oauth2Login()
					.withObjectPostProcessor(new RateLimiteAuthenticationProviderProcessor<>(OidcAuthorizationCodeAuthenticationProvider.class))
				.and().apply(new RobotAccountConfigurer())
					.password("beep-boop")
					.password("boop-beep")
				.and()
				.build();
		// @formatter:on
	}

	@Bean
	UserDetailsService userDetailsService() {
		return new InMemoryUserDetailsManager(
				new User("user", "{noop}password", Collections.emptyList())
		);
	}


	@Bean
	ApplicationListener<AuthenticationSuccessEvent> authSuccess() {
		return event -> {
			var auth = event.getAuthentication();
			LoggerFactory.getLogger(SecurityConfig.class).info("LOGIN SUCCESFUL [{}] - {}", auth.getClass().getSimpleName(), auth.getName());
		};
	}
}
