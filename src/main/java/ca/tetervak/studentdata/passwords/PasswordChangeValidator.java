package ca.tetervak.studentdata.passwords;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasswordChangeValidator implements Validator {

    @Override
    public boolean supports(Class<?> type) {
        return PasswordChangeForm.class.isAssignableFrom(type);
    }

    @Override
    public void validate(Object object, Errors errors) {

        PasswordChangeForm form = (PasswordChangeForm) object;

        String currentPassword = form.getCurrentPassword().trim();
        String newPassword1 = form.getNewPassword1().trim();
        String newPassword2 = form.getNewPassword2().trim();

        if (currentPassword.isEmpty()) {
            errors.rejectValue("currentPassword", "currentPassword.empty");
        }

        if (newPassword1.isEmpty()) {
            errors.rejectValue("newPassword1", "newPassword.empty");
        }

        if (newPassword2.isEmpty()) {
            errors.rejectValue("newPassword2", "newPassword.empty");
        } else {
            if (!newPassword1.equals(newPassword2)) {
                errors.rejectValue("newPassword2", "newPassword.noMatch");
            }
        }
    }
}
