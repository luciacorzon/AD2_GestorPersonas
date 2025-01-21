package org.example;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
class SexoConverter implements AttributeConverter<Sexo, Character> {

    // Para convertilo na BD
    @Override
    public Character convertToDatabaseColumn(Sexo sexo) {
        if (sexo == null) {
            return null;
        }
        return sexo == Sexo.HOMBRE ? 'M' : 'F';
    }

    // Para convertilo en atributo dunha entidade
    @Override
    public Sexo convertToEntityAttribute(Character dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData == 'M' ? Sexo.HOMBRE : Sexo.MUJER;
    }
}
