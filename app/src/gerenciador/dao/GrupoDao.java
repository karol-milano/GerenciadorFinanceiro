package gerenciador.dao;

import gerenciador.model.Categoria;
import gerenciador.model.Grupo;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

public class GrupoDao {

    protected EntityManagerFactory emf;

    public GrupoDao() {
        this.emf = JPAUtil.getInstance().getEntityManagerFactory();
    }

    public Grupo cadastrarGrupo(Grupo g) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (g.getIdGrupo() != null) {
            g = em.merge(g);
        } else {
            em.persist(g);
        }
        em.getTransaction().commit();
        em.close();
        return g;
    }

    public void excluirGrupo(Grupo g) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(g);
        em.getTransaction().commit();
        em.close();
    }

    public Grupo findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Grupo g = em.find(Grupo.class, id);
        em.close();
        return g;
    }

    public Grupo findByNome(String nomeGrupo) {
        Grupo g = null;
        EntityManager em = emf.createEntityManager();
        try {            
            Query query = em.createQuery("SELECT g FROM Grupo g WHERE g.nome LIKE :nomeGrupo");
            query.setParameter("nomeGrupo", nomeGrupo);
            g = (Grupo) query.getSingleResult();
        } catch (NoResultException nre) {

        }
        em.close();
        return g;
    }

    public ArrayList<Grupo> getAll() {
        ArrayList<Grupo> grupos = null;
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("SELECT g FROM Grupo g");
        grupos = (ArrayList<Grupo>) query.getResultList();
        em.close();
        return grupos;
    }

    public ArrayList<Grupo> getByCategoria(Categoria c) {
        ArrayList<Grupo> grupos = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT g FROM Grupo g WHERE g.categoria.id = :idCategoria");
            query.setParameter("idCategoria", c.getIdCategoria());
            grupos = (ArrayList<Grupo>) query.getResultList();
        } catch (NoResultException nre) {

        }
        em.close();
        return grupos;
    }
}
