package portal.education.Monitor;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder encoder;

    static String SYSTEM_LOGIN;

    static String SYSTEM_PASSWORD;

    static String ADMIN_LOGIN;

    static String ADMIN_PASSWORD;

    private final AdminServerProperties adminServer;

    public WebSecurityConfig(AdminServerProperties adminServer) {
        this.adminServer = adminServer;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder())
                .withUser(SYSTEM_LOGIN).password(encoder.encode(SYSTEM_PASSWORD)).roles("SYSTEM")
                .and()
                .withUser(ADMIN_LOGIN).password(encoder.encode(ADMIN_PASSWORD)).roles("ADMIN");
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/", this.adminServer.getContextPath() + "/**").hasRole("ADMIN")
                .antMatchers(this.adminServer.getContextPath() + "/assets/**").permitAll()
                .antMatchers(this.adminServer.getContextPath() + "/sba-settings.js").permitAll()
                .antMatchers(this.adminServer.getContextPath() + "/login").permitAll()
                .antMatchers("/manage/health**", "/actuator/**").permitAll()
                .antMatchers("/eureka/css/**", "/eureka/images/**", "/eureka/fonts/**", "/eureka/js/**").permitAll()
                .antMatchers("/eureka/**").hasRole("SYSTEM")
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Value("${myeureka.server.system.login}")
    public void setSystemLogin(String systemLogin) {
        this.SYSTEM_LOGIN = systemLogin;
    }

    @Value("${myeureka.server.system.password}")
    public void setSystemPassword(String systemPassword) {
        this.SYSTEM_PASSWORD = systemPassword;
    }

    @Value("${control.server.admin.login}")
    public void setAdminLogin(String adminLogin) {
        this.ADMIN_LOGIN = adminLogin;
    }

    @Value("${control.server.admin.password}")
    public void setAdminPassword(String adminPassword) {
        this.ADMIN_PASSWORD = adminPassword;
    }

}


