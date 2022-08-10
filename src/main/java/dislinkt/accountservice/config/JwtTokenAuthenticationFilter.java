package dislinkt.accountservice.config;

import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import dislinkt.accountservice.dtos.Authority;
import dislinkt.accountservice.services.impl.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;
	private JwtTokenProvider tokenProvider;

	public JwtTokenAuthenticationFilter(JwtConfig jwtConfig, JwtTokenProvider tokenProvider) {

		this.jwtConfig = jwtConfig;
		this.tokenProvider = tokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String header = request.getHeader(jwtConfig.getHeader());

		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			chain.doFilter(request, response);
			return;
		}

		String token = header.replace(jwtConfig.getPrefix(), "");

		if (tokenProvider.validateToken(token)) {
			Claims claims = tokenProvider.getClaimsFromJWT(token);
			String username = claims.getSubject();
			List<String> authoritiesNames = (List<String>) claims.get("authorities");

			List<Authority> authorities = new ArrayList<Authority>();
			authoritiesNames.forEach(name -> authorities.add(new Authority(1L, name)));

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(null, null,
					authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			SecurityContextHolder.clearContext();
		}

		chain.doFilter(request, response);
	}

}