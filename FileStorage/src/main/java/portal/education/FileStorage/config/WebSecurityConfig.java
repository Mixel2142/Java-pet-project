package portal.education.FileStorage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.*;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.WebSessionServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFluxSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private SecurityContextRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    static String ACTUATOR_LOGIN;

    static String ACTUATOR_PASSWORD;

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .passwordEncoder(encoder::encode)
                .username(ACTUATOR_LOGIN)
                .password(ACTUATOR_PASSWORD)
                .roles("ACTUATOR")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/storage/**"))
                .cors().disable()
                .csrf().disable()
                .httpBasic().disable()
                .authenticationManager(manager)
                .securityContextRepository(repository)
                .authorizeExchange()
                .pathMatchers(HttpMethod.GET, "/storage/file").permitAll()
//                .pathMatchers("/**").hasRole("SYSTEM")//
//                .pathMatchers("/**").hasRole("USER")
//                .pathMatchers("/**").permitAll()
                .and()
                // всё, что ниже это общее для всех
                .securityMatcher(ServerWebExchangeMatchers.pathMatchers("/actuator/**","/manage/**", "/swagger-ui.html", "/webjars/**", "/api-docs/**"))
                .cors().disable()
                .csrf().disable()
                .httpBasic().and()
                .authenticationManager(new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService()))
                .securityContextRepository(new WebSessionServerSecurityContextRepository())
                .authorizeExchange()
                // TODO EUREKA {
                .pathMatchers("/manage/health**", "/actuator/**").hasRole("ACTUATOR") // TODO сделать роль и пароль SYSTEM
                // TODO EUREKA }
                // TODO SWAGGER {
                .pathMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .pathMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/api-docs/**").permitAll()
                // TODO SWAGGER }
                .anyExchange().denyAll()
                .and()
                .build();
    } // эта труда работает снизу вверх

    @Bean
    public PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        encoders.put("noop", NoOpPasswordEncoder.getInstance());
        encoders.put("pbkdf2", new Pbkdf2PasswordEncoder());
        encoders.put("scrypt", new SCryptPasswordEncoder());
        encoders.put("sha256", new StandardPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }

    @Value("${eureka.instance.metadata-map.user.name}")
    public void setActuatorLogin(String actuatorLogin) {
        this.ACTUATOR_LOGIN = actuatorLogin;
    }

    @Value("${eureka.instance.metadata-map.user.password}")
    public void setActuatorPassword(String actuatorPassword) {
        this.ACTUATOR_PASSWORD = actuatorPassword;
    }
}
