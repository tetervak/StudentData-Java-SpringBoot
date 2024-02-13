package ca.tetervak.studentdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
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
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        // remove "h2-console" from the program in production
        httpSecurity.authorizeHttpRequests(
                (authorize) -> authorize
                        .requestMatchers(
                        "/css/**", "/js/**", "/", "/index", "/h2-console/**")
                        .permitAll()
                        .requestMatchers("/users/**")
                        .hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
        );

        // this line is necessary for h2-console, it reduces security
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.formLogin(
                        (login) -> login
                                .loginPage("/login")
                                .defaultSuccessUrl("/index")
                                .failureUrl("/login?error=true")
                                .permitAll()
                );

        httpSecurity.logout(
                (logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/index")
                        .deleteCookies("remember-me")
                        .permitAll()
                );

        httpSecurity.rememberMe(
                (remember) -> remember
                        .rememberMeCookieName("remember-me")
                        .tokenRepository(persistentTokenRepository())
                        .tokenValiditySeconds(24 * 60 * 60)
        );

        return httpSecurity.build();
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
