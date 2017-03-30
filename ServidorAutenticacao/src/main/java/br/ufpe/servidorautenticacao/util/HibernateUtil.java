/**
 * 
 */
package br.ufpe.servidorautenticacao.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Endrigo
 *
 */
public class HibernateUtil {

	private static EntityManagerFactory entityManagerFactory = null;
    static {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("h2");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }
    
    public static void close(){
    	entityManagerFactory.close();
    }


}
