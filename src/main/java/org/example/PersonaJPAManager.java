package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class PersonaJPAManager {
    public static final String PERSONAS_H2 = "personash2";

    private static final Map<String, EntityManagerFactory> instancies = new HashMap<>();

    private PersonaJPAManager() {
    }

    // Comproba se o EntityManagerFactory asociado á unidade de persistencia que se lle pasa
    // está pechado ou non e se existe
    private static boolean isEntityManagerFactoryClosed(String unidadPersistencia){
        return !instancies.containsKey(unidadPersistencia) || instancies.get(unidadPersistencia) == null
                || !instancies.get(unidadPersistencia).isOpen();
    }

    // Devolve o EntityManagerFactory asociado á unidade de persistencia
    public static EntityManagerFactory getEntityManagerFactory(String unidadPersistencia){
        if (isEntityManagerFactoryClosed(unidadPersistencia)) {
            synchronized (PersonaJPAManager.class) {
                if (isEntityManagerFactoryClosed(unidadPersistencia)){
                    try {
                        instancies.put(unidadPersistencia, Persistence.createEntityManagerFactory(unidadPersistencia));
                    } catch (Exception e){
                        System.err.println("Erro ó crear a unidade de persistencia " + unidadPersistencia + ": " + e.getMessage());
                    }

                }
            }
        }
        return instancies.get(unidadPersistencia);
    }

    // Devolve o entityManager asociado á unidade de persistencia
    public static EntityManager getEntityManager (String persistenceUnitName){
        return getEntityManagerFactory(persistenceUnitName).createEntityManager();
    }

    public static void close(String persistenceUnitName){
        if (instancies.containsKey(persistenceUnitName)){
            instancies.get(persistenceUnitName).close();
            instancies.remove(persistenceUnitName);
        }
    }
}
