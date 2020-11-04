package services;

import entities.User;
import repositories.user.UserRepository;
import services.userService.UserService;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class CheckDataService {
    public static String updatePassword(String password, String name, String email) {
        String updatedPassword = name + password + email;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(updatedPassword.getBytes());
            byte[] digest = md.digest();
            String myHash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            return myHash;
        } catch (NoSuchAlgorithmException e) {
            e.getMessage();
        }
        return null;
    }

    public static boolean checkPasswords(String originalPassword, String name, String email, String encryptedPassword){
        String updatedPassword = updatePassword(originalPassword, name, email);
        return encryptedPassword.equals(updatedPassword);
    }

    //1 - null, 2 - no email
    public static int checkEmail(String email){
        if (email.equals("")){
            return 1;
        }
        else if (!email.matches("^([\\w\\-\\_]+)@(([\\w\\-\\_\\.]+)$)")){
            return 2;
        }
        else {
            return 0;
        }
    }

    //1-no pas, 2 - no repas, 3 - <8, 4 - pas != rePas,x`
    public static int checkPasswordsCorrectness(String password, String repeatPassword){
        if(password.equals("")){
            return 1;
        }
        else if (repeatPassword.equals("")){
            return 2;
        }
        else if (password.length() < 8) {
            return 3;
        }
        else if (!password.equals(repeatPassword)){
            return 4;
        }
        else {
            return 0;
        }
    }

    //1 - licence not on
    public static int checkAgreement(String licence){
        if (licence == null){
            return 1;
        }
        else {
            return 0;
        }
    }

    //1 - already taken, -1 sqlEx
    public static int checkEmailInDb(String email, UserService userService){
        try {
            User user = userService.findByEmail(email);
            if (user == null){
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            return -1;
        }

    }

    //1 - name <2
    public static int checkName(String name){
        if (name.length() < 2){
            return 1;
        } else {
            return 0;
        }
    }
}
