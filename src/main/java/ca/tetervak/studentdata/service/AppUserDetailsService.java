package ca.tetervak.studentdata.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AppUserDetailsService implements UserDetailsService {

    private final Logger logger = LoggerFactory.getLogger(AppUserDetailsService.class);

    private final AppLoginDataService loginDataService;

    public AppUserDetailsService(AppLoginDataService loginDataService) {

        this.loginDataService = loginDataService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        logger.trace("loadUserByUsername() is called");
        if(loginDataService.userExists(login)){
            logger.trace("user " + login + " is found");
            String password = loginDataService.getPassword(login);
            return new User(login, password, getAuthorities(login));
        }else{
            logger.trace("user " + login + " is not found");
            throw new UsernameNotFoundException("Login " + login + " is not found");
        }
    }

    private List<GrantedAuthority> getAuthorities(String login) {
        logger.trace("getAuthorities() is called");
        List<String> listOfRoles = loginDataService.getAllRoleNames(login);
        String[] arrayOfRoles = listOfRoles.toArray(new String[0]);
        logger.trace("roles for login=" +
                login + ":[" + String.join(",", arrayOfRoles) + "]");
        return AuthorityUtils.createAuthorityList(arrayOfRoles);
    }

}
