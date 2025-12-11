package cr.ac.ucenfotec.sortiz0640.bl.util;
import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Utilidad para la encriptación de contraseñas utilizando el algoritmo BCrypt.
 *
 * @author Sebastian Ortiz
 * @version 1.0
 * @since 2025
 */
public class PasswordEncrypt {

    /**
     * Encripta una contraseña en texto plano utilizando BCrypt.
     *
     * @param password Contraseña original en texto plano.
     * @return Hash de la contraseña resultante.
     */
    public static String encrypt(String password) {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray());
    }
}