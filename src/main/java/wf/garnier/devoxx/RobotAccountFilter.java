package wf.garnier.devoxx;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class RobotAccountFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (!Collections.list(request.getHeaderNames()).contains("x-robot-password")) {
			filterChain.doFilter(request, response);
			return;
		}

		String password = request.getHeader("x-robot-password");
		if ("beep-boop".equals(password)) {
			var newContext = SecurityContextHolder.createEmptyContext();
			newContext.setAuthentication(new RobotAuthentication());
			SecurityContextHolder.setContext(newContext);
			filterChain.doFilter(request, response);
		} else {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setCharacterEncoding("utf-8");
			response.getWriter().println("You are not Mr Robot... 🤖");
			return;
		}
	}
}
