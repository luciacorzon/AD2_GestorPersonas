package org.example;

import jakarta.persistence.*;

@Entity
@Access(AccessType.FIELD)
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "direccion_id_gen")
    @TableGenerator(
            name = "direccion_id_gen",
            table = "LONG_ID_GEN",
            pkColumnName = "nomePK",
            valueColumnName = "valorPK",
            pkColumnValue = "DIRECCION_ID",
            initialValue = 2000,
            allocationSize = 50
    )
    private Long idDireccion;

    private String calle;
    private String ciudad;
    private String pais;

    public Direccion() {
    }

    public Direccion(String calle, String ciudad, String pais) {
        this.calle = calle;
        this.ciudad = ciudad;
        this.pais = pais;
    }
}

