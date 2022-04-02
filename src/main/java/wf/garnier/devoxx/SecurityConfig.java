package wf.garnier.devoxx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeRequests()
					.antMatchers("/").permitAll()
					.antMatchers("/error").permitAll()
					.antMatchers("/favicon.ico").permitAll()
					.anyRequest().authenticated()
				.and()
				.build();
	}
}
