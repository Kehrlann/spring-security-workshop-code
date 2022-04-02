package wf.garnier.devoxx;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/")
	public String publicPage() {
		return "Hello World!";
	}

	@GetMapping("private")
	public String privatePage(Authentication authentication) {
		return "Welcome to this very private page, ~[" + getName(authentication) + "]~! 🥳🎉🍾";
	}

	private String getName(Authentication authentication) {
		return Optional.of(authentication)
				.filter(OAuth2AuthenticationToken.class::isInstance)
				.map(OAuth2AuthenticationToken.class::cast)
				.map(OAuth2AuthenticationToken::getPrincipal)
				.map(OidcUser.class::cast)
				.map(OidcUser::getFullName)
				.orElseGet(authentication::getName);
	}
}
