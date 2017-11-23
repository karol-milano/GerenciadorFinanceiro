package gerenciador.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
    
    private static final JPAUtil singleton = new JPAUtil();

    private EntityManagerFactory emf;

    private JPAUtil() {}

    public static JPAUtil getInstance() {
        return singleton;
    }


    public EntityManagerFactory getEntityManagerFactory() {
        if(emf == null) {
            emf = Persistence.createEntityManagerFactory("GerenciadorFinanceiroPU");
        }
        
        return emf;
    }

    public void closeEmf() {
        if(emf.isOpen() || emf != null) {
            emf.close();
        }
        emf = null;
    }
}
