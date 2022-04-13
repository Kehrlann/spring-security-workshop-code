package wf.garnier.devoxx;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFilter;

public class RobotAccountFilter extends AuthenticationFilter {

	public RobotAccountFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager, RobotAccountFilter::convert);
		super.setFailureHandler(RobotAccountFilter::onFailure);
		super.setSuccessHandler(RobotAccountFilter::onSuccess);
	}

	private static Authentication convert(HttpServletRequest request) {
		if (!Collections.list(request.getHeaderNames()).contains("x-robot-password")) {
			return null;
		}

		String password = request.getHeader("x-robot-password");
		return RobotAuthentication.authenticationToken(password);
	}

	private static void onFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException {
		res.setStatus(HttpStatus.UNAUTHORIZED.value());
		res.setCharacterEncoding("utf-8");
		res.getWriter().println("You are not Mr Robot... 🤖");
	}

	private static void onSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth) throws IOException {
		// noop
	}
}
