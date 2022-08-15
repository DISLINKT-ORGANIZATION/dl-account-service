package dislinkt.accountservice.config;

import io.jsonwebtoken.Claims;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;

import dislinkt.accountservice.dtos.Authority;
import dislinkt.accountservice.exceptions.InvalidToken;
import dislinkt.accountservice.services.impl.JwtTokenProvider;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

	@Value("${auth-service.address}")
	private String authServiceAddress;
	
    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private JwtTokenProvider tokenProvider;

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
			RestTemplate restTemplate = new RestTemplate();
			String fooResourceUrl = authServiceAddress + "/authentication/users/check-username/" + username;
			ResponseEntity<Boolean> restTemplateResponse = restTemplate.getForEntity(fooResourceUrl, Boolean.class);
			if (!restTemplateResponse.getBody()) {
				throw new InvalidToken("Username not found on authentication service.");
			}

			List<String> authoritiesNames = (List<String>) claims.get("authorities");
			Long id = ((Integer) claims.get("id")).longValue();

			List<Authority> authorities = new ArrayList<Authority>();
			authoritiesNames.forEach(name -> authorities.add(new Authority(1L, name)));

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(id, null,
					authorities);
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			SecurityContextHolder.clearContext();
		}

		chain.doFilter(request, response);
	}

}