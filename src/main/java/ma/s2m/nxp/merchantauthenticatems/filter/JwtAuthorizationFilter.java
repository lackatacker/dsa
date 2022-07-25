package ma.s2m.nxp.merchantauthenticatems.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import ma.s2m.nxp.merchantauthenticatems.utils.JWTutils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class JwtAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getServletPath().equals("/refreshToken") || request.getServletPath().equals("/login")) {
			filterChain.doFilter(request, response);
		}
		else {
			String authToken = request.getHeader(JWTutils.AUTH_HEADER);

			if (authToken != null && authToken.startsWith(JWTutils.TOKEN_PREFIX)) {
				try {
					String jwt = authToken.substring(JWTutils.TOKEN_PREFIX.length());
					Algorithm algorithm = Algorithm.HMAC256(JWTutils.SECRET_KEY);
					JWTVerifier jwtVerifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = jwtVerifier.verify(jwt);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					Collection<GrantedAuthority> authorities = new ArrayList<>();
					for (String r : roles) {
						System.out.println("Adding: " + r);
						authorities.add(new SimpleGrantedAuthority(r));
					}
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				}
				catch (Exception e) {
					response.setHeader("Error Message", e.getMessage());
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			}
			else {
				filterChain.doFilter(request, response);// SpringSec passe au filtre
														// suivant mais sans jwt auth
			}

		}
	}

}
