package utn.donatrack.donatrack_frontend.viewmodel;

/** Chequeos simples compartidos por los formularios. Cada formulario devuelve el primer error como texto para mostrar en un banner. */
final class Validaciones {

    private Validaciones() {}

    static boolean vacio(String s) {
        return s == null || s.isBlank();
    }

    static String limpio(String s) {
        return vacio(s) ? null : s.strip();
    }

    static boolean correoValido(String s) {
        return !vacio(s) && s.strip().matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    }

    /** Contrasena del auto-registro: minimo 8 caracteres y repetida igual. Devuelve el error o null. */
    static String clave(String clave, String repetida) {
        if (vacio(clave) || clave.length() < 8) {
            return "La contraseña debe tener al menos 8 caracteres.";
        }
        if (!clave.equals(repetida)) {
            return "Las contraseñas no coinciden.";
        }
        return null;
    }
}
