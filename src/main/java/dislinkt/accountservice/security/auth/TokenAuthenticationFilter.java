package dislinkt.accountservice.security.auth;

import dislinkt.accountservice.dtos.Authority;
import dislinkt.accountservice.exceptions.InvalidToken;
import dislinkt.accountservice.security.TokenUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenUtils tokenUtils;

    public TokenAuthenticationFilter(TokenUtils tokenHelper) {
        this.tokenUtils = tokenHelper;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            // uzmi username iz tokena
            String username = tokenUtils.getUsernameFromToken(authToken);
            Claims claims = tokenUtils.getAllClaimsFromToken(authToken);
            if (username != null) {
                RestTemplate restTemplate = new RestTemplate();
                String fooResourceUrl = "http://localhost:8081/auth-service/authentication/users/check-username/" + username;
                ResponseEntity<Boolean> restTemplateResponse = restTemplate.getForEntity(fooResourceUrl, Boolean.class);
                if (!restTemplateResponse.getBody()) {
                    throw new InvalidToken("Username not found on authentication service.");
                }

                List<String> authoritiesNames = (List<String>) claims.get("authorities");
                Long id = Long.parseLong(claims.get("id") + "");

                List<Authority> authorities = new ArrayList<>();
                authoritiesNames.forEach(name -> authorities.add(new Authority(1L, name)));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(id, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

}
