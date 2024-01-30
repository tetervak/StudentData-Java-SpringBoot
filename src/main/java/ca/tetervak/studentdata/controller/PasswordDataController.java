package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.passwords.PasswordChangeForm;
import ca.tetervak.studentdata.passwords.PasswordChangeValidator;
import ca.tetervak.studentdata.service.AppLoginDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/password/*")
public class PasswordDataController {

    private final AppLoginDataService loginDataService;

    public PasswordDataController(AppLoginDataService loginDataService) {
        this.loginDataService = loginDataService;
    }

    @InitBinder("pcform")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new PasswordChangeValidator());
    }

    // a user clicks "Change password" link
    @GetMapping("/change-password")
    public String changePassword(Model model) {
        log.trace("changePassword() is called");
        model.addAttribute("pcform", new PasswordChangeForm());
        return "ChangePassword";
    }

    // a user clicks "Change Password" button in "ChangePassword.html",
    // the form submits data to "/UpdatePassword.do"
    @PostMapping("/update-password")
    public String updatePassword(
            @Validated @ModelAttribute("pcform") PasswordChangeForm pcform,
            BindingResult result) {
        log.trace("updatePassword() is called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        if (!result.hasFieldErrors("currentPassword")) {
            if (!loginDataService.checkPassword(login, pcform.getCurrentPassword().trim())) {
                result.rejectValue("currentPassword", "currentPassword.wrong");
                log.trace("the entered current password is wrong");
            }
        }

        if (result.hasErrors()) {
            log.trace("input validation errors");
            return "ChangePassword";
        } else {
            loginDataService.updatePassword(login, pcform.getNewPassword1().trim());
            log.trace("the password is updated");
            return "PasswordChanged";
        }
    }

}
