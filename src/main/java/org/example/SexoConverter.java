package org.example;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// O converter modifica o tipo de dato do atributo
// En este caso, convirte de Char a Sexo e viceversa
// Autoapply indica que se aplique a todos os atributos de tipo Sexo automaticamente
@Converter(autoApply = true)
class SexoConverter implements AttributeConverter<Sexo, Character> {

    // Para convertilo na BD
    @Override
    public Character convertToDatabaseColumn(Sexo sexo) {
        if (sexo == null) {
            return null;
        }
        return sexo == Sexo.HOMBRE ? 'M' : 'F';

        // Versión de clase:
        // return (sexo!=null) ? sexo.name().charAt(0) : null;
    }

    // Para convertilo en atributo dunha entidade
    @Override
    public Sexo convertToEntityAttribute(Character dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData == 'M' ? Sexo.HOMBRE : Sexo.MUJER;

        // Versión de clase:
        // return Sexo.of(character);
    }
}
