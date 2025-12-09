package cr.ac.ucenfotec.sortiz0640.bl.util;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordEncrypt {

    public static String encrypt(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}
