package org.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Persona p1 = new Persona("Juan", "Pérez", LocalDate.of(1980, 1, 1), Sexo.HOMBRE, EstadoCivil.SOLTERO);
        Persona p2 = new Persona("María", "Gómez", LocalDate.of(1985, 2, 2), Sexo.MUJER, EstadoCivil.CASADO);
        Persona p3 = new Persona("Ana", "López", LocalDate.of(1990, 3, 3), Sexo.MUJER, EstadoCivil.SOLTERO);

        var etf = PersonaJPAManager.getEntityManagerFactory(PersonaJPAManager.PERSONAS_H2);
        var em = etf.createEntityManager();

        // Persister as persoas
        try {
            em.getTransaction().begin();
            em.persist(p1);
            em.persist(p2);
            em.persist(p3);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.err.println("Erro ó gardar as persoas: " + e.getMessage());
            em.getTransaction().rollback();
        }


        // Recuperar e ler as persoas da base de datos
        try {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT p FROM Persona p", Persona.class);
            var persoas = query.getResultList();
            em.getTransaction().commit();
            for (Persona p : persoas) {
                System.out.println(p);
            }
        } catch (Exception e) {
            System.err.println("Erro ó ler as persoas: " + e.getMessage());
            em.getTransaction().rollback();
        }
    }
}