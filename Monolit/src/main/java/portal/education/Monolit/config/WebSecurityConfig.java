package portal.education.Monolit.config;

import org.springframework.security.core.userdetails.User;
import portal.education.Monolit.filter.token.AuthenticationJwtFilter;
import portal.education.Monolit.service.person.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ComponentScan(basePackages = {"portal.education.Monolit.security"})
//@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationJwtFilter jwtRequestFilter;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()// We don't need CSRF for this example
                .cors().disable()
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/hellofree").permitAll()
                .antMatchers("/hellonotfree").hasRole("USER")
                .antMatchers("/vendor/graphiql/**").permitAll()// TODO delete on prod
                .antMatchers("/free/**").permitAll()// dont authenticate this particular request
                .antMatchers("/registry/**").hasAnyRole("USER", "AUTHOR", "ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "AUTHOR", "ADMIN")
                .antMatchers("/author/**").hasAnyRole("AUTHOR", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll();// all other requests need to be authenticated


        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(encoder());

        auth.inMemoryAuthentication()
                .withUser("some")
                .password("some")
                .roles("some");
    }

}

