package dislinkt.accountservice.config;

import dislinkt.accountservice.security.TokenUtils;
import dislinkt.accountservice.security.auth.RestAuthenticationEntryPoint;
import dislinkt.accountservice.security.auth.TokenAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Value("${auth-service.address}")
    private String authServiceAddress;

    @Autowired
    private EncoderConfig encoderConfig;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
    // Injektujemo implementaciju iz TokenUtils klase kako bismo mogli da koristimo
    // njene metode za rad sa JWT u TokenAuthenticationFilteru
    @Autowired
    private TokenUtils tokenUtils;

    // Registrujemo authentication manager koji ce da uradi autentifikaciju
    // korisnika za nas
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // Definisemo prava pristupa odredjenim URL-ovima
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests().antMatchers("/actuator/**").permitAll().anyRequest().authenticated().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenUtils, authServiceAddress), BasicAuthenticationFilter.class);
//			.cors().configurationSource(request -> config)
        http.csrf().disable();
    }

    // Generalna bezbednost aplikacije
    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
//        web.ignoring().antMatchers(HttpMethod.GET, "/accounts/skill-proficiencies/type");
        web.ignoring().antMatchers(HttpMethod.POST, "/accounts/public-search");
        web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/favicon.ico", "/**/*.html",
                "/**/*.css", "/**/*.js");
    }
}
