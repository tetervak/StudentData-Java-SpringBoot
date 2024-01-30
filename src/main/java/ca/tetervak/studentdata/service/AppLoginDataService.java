package ca.tetervak.studentdata.service;

import ca.tetervak.studentdata.data.entities.AppRole;
import ca.tetervak.studentdata.data.entities.AppUser;
import ca.tetervak.studentdata.data.repositories.AppRoleDataRepository;
import ca.tetervak.studentdata.data.repositories.AppUserDataRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppLoginDataService{

    private final AppUserDataRepository userDataRepository;
    private final AppRoleDataRepository roleDataRepository;
    private final PasswordEncoder passwordEncoder;

    public AppLoginDataService(
            AppUserDataRepository userDataRepository,
            AppRoleDataRepository roleDataRepository,
            PasswordEncoder passwordEncoder) {
        this.userDataRepository = userDataRepository;
        this.roleDataRepository = roleDataRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public boolean userExists(String userName) {
        return userDataRepository.findByUserName(userName) != null;
    }


    public void insertUser(String userName, String password) {
        AppUser user = new AppUser();
        user.setUserName(userName);
        user.setPassword(passwordEncoder.encode(password));
        userDataRepository.save(user);
    }

    public void insertRole(String userName, String roleName) {
        AppUser user = userDataRepository.findByUserName(userName);
        if(user != null){
            AppRole role = roleDataRepository.findByRoleName(roleName);
            if(role != null){
                user.getRoles().add(role);
                userDataRepository.save(user);
            }
        }
    }

    public void removeUser(String userName) {
        userDataRepository.deleteByUserName(userName);
    }

    public void removeRole(String userName, String roleName) {
        AppUser user = userDataRepository.findByUserName(userName);
        if(user != null){
            List<AppRole> roles = user.getRoles();
            for(AppRole role: roles){
                if(role.getRoleName().equals(roleName)){
                    roles.remove(role);
                    break;
                }
            }
            userDataRepository.save(user);
        }
    }

    public void removeRoles(String userName) {
        AppUser user = userDataRepository.findByUserName(userName);
        if(user != null) {
            user.getRoles().clear();
            userDataRepository.save(user);
        }
    }

    public List<String> getAllUserNames(String roleName) {
        List<String> userNames = new ArrayList<>();
        AppRole role = roleDataRepository.findByRoleName(roleName);
        if(role != null){
            List<AppUser> users = role.getUsers();
            for(AppUser user: users){
                userNames.add(user.getUserName());
            }
        }
        return userNames;
    }

    public List<String> getAllRoleNames(String userName) {
        List<String> roleNames = new ArrayList<>();
        AppUser user = userDataRepository.findByUserName(userName);
        if(user != null){
            List<AppRole> roles = user.getRoles();
            for(AppRole role: roles){
                roleNames.add(role.getRoleName());
            }
        }

        return roleNames;
    }

    public void updatePassword(String userName, String password) {
        AppUser user = userDataRepository.findByUserName(userName);
        if(user != null){
            user.setPassword(passwordEncoder.encode(password));
            userDataRepository.save(user);
        }
    }

    public boolean checkPassword(String userName, String password) {
        String storedPassword = getPassword(userName);
        if(storedPassword != null) {
            return passwordEncoder.matches(password, storedPassword);
        }else{
            return false;
        }
    }

    public String getPassword(String userName) {
        AppUser user = userDataRepository.findByUserName(userName);
        if(user != null){
            return user.getPassword();
        }else{
            return null;
        }
    }
}
