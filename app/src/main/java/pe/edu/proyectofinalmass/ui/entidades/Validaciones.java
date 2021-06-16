package pe.edu.proyectofinalmass.ui.entidades;

import android.util.Patterns;

public class Validaciones {

    //metodo para validar si es un email
    public  boolean isEmail(String cadena) {
        boolean resultado;
        if (Patterns.EMAIL_ADDRESS.matcher(cadena).matches()) {
            resultado = true;
        } else {
            resultado = false;
        }
        return resultado;
    }
}

