package org.example;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;

@Entity
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idPersona;

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

    @Enumerated(EnumType.STRING)
    private EstadoCivil estadoCivil;

    // Marcado como BLOB
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
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        updateNombreApellidos();
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
