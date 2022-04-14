package wf.garnier.devoxx;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static java.time.Instant.now;

public class RateLimitedAuthenticationProvider implements AuthenticationProvider {
	private final AuthenticationProvider delegate;
	private final Map<String, Instant> authCache = new HashMap<>();

	public RateLimitedAuthenticationProvider(AuthenticationProvider delegate) {
		this.delegate = delegate;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		var parentAuthentication = this.delegate.authenticate(authentication);
		var success = renewCacheEntry(parentAuthentication);
		if (success) {
			return parentAuthentication;
		} else {
			throw new BadCredentialsException("Cannot log in right now: rate limited");
		}
	}

	synchronized public boolean renewCacheEntry(Authentication authentication) {
		var now = now();
		var previousLogin = authCache.get(authentication.getName());
		if (previousLogin == null || previousLogin.plusSeconds(60).isBefore(now)) {
			authCache.put(authentication.getName(), now);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return delegate.supports(authentication);
	}

}
