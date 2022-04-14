package wf.garnier.devoxx;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;

public class RateLimiteAuthenticationProviderProcessor<T extends AuthenticationProvider> implements ObjectPostProcessor<T> {
	private Class<T> clazz;

	public RateLimiteAuthenticationProviderProcessor(Class<T> clazz) {
		this.clazz = clazz;
	}

	@Override
	public <O extends T> O postProcess(O object) {
		if (clazz.isAssignableFrom(object.getClass())) {
			return (O) new RateLimitedAuthenticationProvider(object);
		}
		return object;
	}
}
