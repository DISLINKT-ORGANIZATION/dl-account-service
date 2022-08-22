package dislinkt.accountservice.security.auth;

import dislinkt.accountservice.security.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Autowired
    private TokenUtils tokenUtils;

    //Metoda koja se izvrsava ukoliko za prosledjene kredencijale korisnik pokusa da pristupi zasticenom REST servisu
    //Metoda vraca 401 Unauthorized response, ukoliko postoji Login Page u aplikaciji, pozeljno je da se korisnik redirektuje na tu stranicu
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        String authToken = tokenUtils.getToken(request);

        if (authToken != null) {
            // uzmi username iz tokena
            String username = tokenUtils.getUsernameFromToken(authToken);
            if (username != null) {
                RestTemplate restTemplate = new RestTemplate();
                String fooResourceUrl = "http://localhost:8081/auth-service/authentication/users/check-username/" + username;
                ResponseEntity<Boolean> restTemplateResponse = restTemplate.getForEntity(fooResourceUrl, Boolean.class);
                if (!restTemplateResponse.getBody()) {
//                    throw new InvalidToken("Username not found on authentication service.");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                }
            }
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        }
    }
}
