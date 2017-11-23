package gerenciador.dao;

import gerenciador.model.Cliente;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class ClienteDao {

    protected EntityManagerFactory emf;

    public ClienteDao() {
        this.emf = JPAUtil.getInstance().getEntityManagerFactory();
    }

    public Cliente cadastrarCliente(Cliente c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (c.getId() != null) {
            c = em.merge(c);
        } else {
            em.persist(c);
        }
        em.getTransaction().commit();
        em.close();
        return c;
    }

    public void excluirCliente(Cliente c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        em.close();
    }

   //// public Cliente findById(Long id) {
       // EntityManager em = emf.createEntityManager();
        //Cliente c = em.find(Cliente.class, id);
        //em.close();
        //return c;
    //}

    public Cliente findByUsuario(String usuario) {
        Cliente c = null;
        EntityManager em = emf.createEntityManager();
        try {            
            Query query = em.createQuery("SELECT c FROM Cliente c WHERE c.usuario LIKE :usuario");
            query.setParameter("usuario", usuario);
            c = (Cliente) query.getSingleResult();
        } catch (NoResultException nre) {
            
        }
        em.close();
        return c;
    }

    public ArrayList<Cliente> getAll() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT c FROM Cliente c");
        ArrayList<Cliente> clientes = (ArrayList<Cliente>) query.getResultList();
        em.close();
        return clientes;
    }
}
