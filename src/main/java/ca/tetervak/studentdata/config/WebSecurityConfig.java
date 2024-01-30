package ca.tetervak.studentdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    DataSource dataSource;

    WebSecurityConfig(
            UserDetailsService userDetailsService,
            AuthenticationManagerBuilder auth,
            DataSource dataSource,
            PasswordEncoder passwordEncoder
    ) throws Exception {
        this.dataSource = dataSource;
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {

        security.headers().frameOptions().sameOrigin();

        security.authorizeHttpRequests()
                // remove "h2-console" from the program in production
                .requestMatchers("/css/**", "/js/**", "/", "/index","/h2-console/**")
                .permitAll();

        // this line is for h2-console, it reduces security
        security.csrf().disable();

        security.authorizeHttpRequests()
                .requestMatchers("/users/**").hasRole("ADMIN")
                .anyRequest()
                .authenticated();

        security.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error=true")
                .permitAll();

        security.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index")
                .deleteCookies("remember-me")
                .permitAll();

        security.rememberMe()
                .rememberMeCookieName("remember-me")
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(24 * 60 * 60);

        return security.build();
    }

    private PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepositoryImpl = new JdbcTokenRepositoryImpl();
        tokenRepositoryImpl.setDataSource(dataSource);
        return tokenRepositoryImpl;
    }

    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }
}
