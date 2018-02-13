package BusinessClasses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorInputs {

    public boolean validateName(String name) {
        if (!name.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean validateEmail(String email) {
        if (email != null) {
            Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
            Matcher m = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            return m.find();
        }
        return false;

    }

    public boolean validatePhone(String phone) {

        String regex = "^\\+?[0-9. ()-]{10,11}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);

        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public boolean validatePassword(String password) {
//        //String expresion = "(?!^[0-9]*$)(?!^[a-zA-Z]*$)^([a-zA-Z0-9]{8,16})$";
//        String expresion = "^(?=.*[0-9])(?=.*[a-z])(?=\\\\S+$).{6,}$\n";
//        Pattern patron = Pattern.compile(expresion);
//        Matcher m = patron.matcher(password);
//        if (m.find()) {
//            return true;
//        }
//        return true;
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public boolean matchPassword(String password1, String password2) {
        if (password1.equals(password2)) {
            return true;
        }
        return false;
    }

    public boolean validateTown(String town) {
        if (!town.trim().isEmpty()) {
            return true;
        }
        return false;
    }

    public boolean validateEmailviaDB(String Email) {
        return true;

    }

    public boolean validatePasswordviaDB(String password) {
        return true;

    }


}
