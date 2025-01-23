package org.example;

public enum Sexo {
    HOMBRE, MUJER;

    // Versión de clase:
    public static Sexo of(Character c) {
        return switch (c) {
            case 'M' -> Sexo.HOMBRE;
            case 'F' -> Sexo.MUJER;
            default -> null;
        };
    }
    /*
        El método 'of' convierte un carácter ('M' o 'F') en el
        valor correspondiente del enum 'Sexo'.
        'M' se convierte en Sexo.HOMBRE y 'F' en Sexo.MUJER.
        Si el carácter no es 'M' ni 'F', retorna null.
        Sexo sexo = Sexo.of('M');
     */
}
