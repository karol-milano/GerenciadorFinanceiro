package gerenciador.dao;

import gerenciador.model.Categoria;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class CategoriaDao {
    
    protected EntityManagerFactory emf;

    public CategoriaDao() {
        this.emf = JPAUtil.getInstance().getEntityManagerFactory();
    }

    public Categoria cadastrarCategoria(Categoria c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (c.getIdCategoria() != null) {
            c = em.merge(c);
        } else {
            em.persist(c);
        }
        em.getTransaction().commit();
        em.close();
        return c;
    }

    public void excluirCategoria(Categoria c) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(c);
        em.getTransaction().commit();
        em.close();
    }

    public Categoria findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Categoria c = em.find(Categoria.class, id);
        em.close();
        return c;
    }

    public Categoria findByNome(String nomeCategoria) {
        Categoria c = null;
        EntityManager em = emf.createEntityManager();
        
        try {            
            Query query = em.createQuery("SELECT c FROM Categoria c WHERE c.nome LIKE :nomeCategoria");
            query.setParameter("nomeCategoria", nomeCategoria);
            c = (Categoria) query.getSingleResult();
        } catch (NoResultException nre) {
            
        }
        
        em.close();
        return c;
    }

    public ArrayList<Categoria> getAll() {
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT c FROM Categoria c");
        ArrayList<Categoria> categorias = (ArrayList<Categoria>) query.getResultList();
        em.close();
        return categorias;
    }
    
    public ArrayList<Categoria> getByTipo(Character tipoCategoria) {
        ArrayList<Categoria> categorias = null;
        EntityManager em = emf.createEntityManager();
        
        try {            
            Query query = em.createQuery("SELECT c FROM Categoria c WHERE c.tipo = :tipoCategoria");
            query.setParameter("tipoCategoria", tipoCategoria);
            categorias = (ArrayList<Categoria>) query.getResultList();
        } catch (NoResultException nre) {
            
        }
        
        em.close();
        return categorias;
    }
}
