package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

@Entity
// AccessType.FIELD indica que as columnas da tabla da BD se
// farán a partir dos atributos da clase automáticamente
// Con esta anotación non seria necesario indicarlle a anotacion
// @Column a cada atributo, a non se que queiramos un nome diferente
@Access(AccessType.FIELD)
public class Persona {
    @Id
    // Para usar a tabla de ids hai que cambiar o strategy e generator
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "long_id_gen")
    @TableGenerator(
            name = "long_id_gen",
            table = "LONG_ID_GEN",
            pkColumnName = "nomePK",
            valueColumnName = "valorPK",
            pkColumnValue = "PERSONA_ID",
            initialValue = 1000,
            allocationSize = 100
    )
    private Long idPersona;

    // Nome e Apelidos como Transient para que se ignoren
    // e solo se añada a columna nombreApellidos que se crea a partir desos
    // dous atributos
    @Column(name = "nombre_apellidos")
    private String nombreApellidos;

    @Transient
    private String nombre;

    @Transient
    private String apellidos;

    // A idade taméns e fai transient para calculala
    // a partir da data de nacemento (outra columna)
    @Transient
    private int edad;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    // Úsase o conversor para cambiar o formato no que se amosa o sexo
    @Convert(converter = SexoConverter.class)
    private Sexo sexo;

    // Por defecto os valores das enumeracións
    // gardanse como ORDINAL, que son números que indican a posición
    // do valor da enumeración
    // Esto é porque as enumeración non son tipos primitivos
    // polo que hai que "procesalas" para gardalas
    // Em este caso, gardanse como STRING
    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    // Marcado como BLOB
    // Indícalle a JPA que debe tratar este
    // atributo como un obxecto de gran tamaño
    @Lob
    private byte[] foto;

    public Persona() {
    }

    public Persona(String nombre, String apellidos, LocalDate fechaNacimiento, Sexo sexo, EstadoCivil estadoCivil) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.estadoCivil = estadoCivil;
        updateNombreApellidos();
        updateEdad();
    }

    // Getters / setters
    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        updateNombreApellidos();
    }

    /*
    Este é un exemplo de como crear unha columna
    a partir dun getter nunha táboa onde se especificou
    que se qieren crear as columnas a partir dos atributos
    Basta con por Accestype.PROPERTY e indicar o nome da columna no getter
    @Access(AccessType.PROPERTY)
    @Column(name = "nombreCompleto")
    */
    public String getNombreCompleto() {
        return nombreApellidos;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
        updateNombreApellidos();
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        updateEdad();
    }

    public int getEdad() {
        return edad;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public EstadoCivil getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(EstadoCivil estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }


    // Poñer o nome en formato Apelidos, Nome
    private void updateNombreApellidos() {
        if (nombre != null && apellidos != null) {
            this.nombreApellidos = String.format("%s, %s", capitalize(apellidos), capitalize(nombre));
        }
    }

    // Poñer en maiúsculas a primeira letra
    private String capitalize(String text) {
        String[] words = text.split(" ");
        StringBuilder capitalized = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                capitalized.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
            }
        }
        return capitalized.toString().trim();
    }

    // Actualizar a idade a partir da data de nacemento
    private void updateEdad() {
        if (fechaNacimiento != null) {
            this.edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }
    }


    // Versión de clase
    // El método marcado con @PostLoad se ejecuta automáticamente
    // después de que la entidad haya sido cargada desde la base de datos,
    // para usarla en el código
    // En este caso, calcula la edad de la persona basada en su fecha de nacimiento.
    /*
    @PostLoad
    public void updateEdad() {
        if (fechaNacimiento != null) {
            //Con ChronoUnit
            edad = (int) ChronoUnit.YEARS.between(fechaNacimiento, LocalDate.now());
            System.out.println("Edad: " + edad);

            //Con temporal
            edad = (int) fechaNacimiento.until(LocalDate.now(), ChronoUnit.YEARS);

            //Con Period
            edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
        } else {
            System.out.println(fechaNacimiento);
        }
    }*/

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombreApellidos='" + nombreApellidos + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", edad=" + edad +
                ", fechaNacimiento=" + fechaNacimiento +
                ", sexo=" + sexo +
                ", estadoCivil=" + estadoCivil +
                ", foto=" + Arrays.toString(foto) +
                '}';
    }
}
