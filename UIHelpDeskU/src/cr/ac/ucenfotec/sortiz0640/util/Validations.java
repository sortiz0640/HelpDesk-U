package cr.ac.ucenfotec.sortiz0640.util;

import org.apache.commons.validator.routines.EmailValidator;

import java.io.IOException;

public class Validations {

    private UI interfaz = new UI();
    EmailValidator validator = EmailValidator.getInstance();

    public String correo() throws IOException {
        String correo = "";
        do {

            interfaz.imprimirMensaje("Ingrese su correo [@gmail.com] : ");
            correo = interfaz.leerTexto();

            if (!validator.isValid(correo) || correo.isBlank()) {
                interfaz.imprimirMensaje("El correo no puede estar vacio y debe cumplir con el formato indicado ");
            }
        } while (correo == null || correo.isBlank() || !validator.isValid(correo));

        return correo;
    }

    public String password() throws IOException {

        String password = "";
        do {
            interfaz.imprimirMensaje("Ingrese su password [mínimo 8 caracteres]");
            password = interfaz.leerTexto();

            if (password == null || password.isBlank() || password.length() < 8) {
                interfaz.imprimirMensaje("El password no puede estar vacio [mínimo 8 caracteres] . ");
            }

        } while (password == null || password.isBlank() || password.length() < 8);

        return password;
    }
}
