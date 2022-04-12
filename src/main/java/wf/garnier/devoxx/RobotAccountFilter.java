package wf.garnier.devoxx;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class RobotAccountFilter extends OncePerRequestFilter {

	private final AuthenticationManager authenticationManager;

	public RobotAccountFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		var authenticationToken = convert(request);
		if (authenticationToken == null) {
			filterChain.doFilter(request, response);
			return;
		}

		try {
			var authentication = authenticationManager.authenticate(authenticationToken);
			var newContext = SecurityContextHolder.createEmptyContext();
			newContext.setAuthentication(authentication);
			SecurityContextHolder.setContext(newContext);
			filterChain.doFilter(request, response);
		} catch (AuthenticationException e) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setCharacterEncoding("utf-8");
			response.getWriter().println("You are not Mr Robot... 🤖");
			return;
		}
	}

	private Authentication convert(HttpServletRequest request) {
		if (!Collections.list(request.getHeaderNames()).contains("x-robot-password")) {
			return null;
		}

		String password = request.getHeader("x-robot-password");
		return RobotAuthentication.authenticationToken(password);
	}
}
