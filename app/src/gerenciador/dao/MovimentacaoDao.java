package gerenciador.dao;

import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import gerenciador.model.Grupo;
import gerenciador.model.Movimentacao;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

public class MovimentacaoDao {

    protected EntityManagerFactory emf;

    public MovimentacaoDao() {
        this.emf = JPAUtil.getInstance().getEntityManagerFactory();
    }

    public Movimentacao cadastrarMovimentacao(Movimentacao m) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        if (m.getIdMovimentacao() != null) {
            m = em.merge(m);
        } else {
            em.persist(m);
        }
        em.getTransaction().commit();
        em.close();
        return m;
    }

    public void excluirMovimentacao(Movimentacao m) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(em.contains(m) ? m : em.merge(m));
        em.getTransaction().commit();
        em.close();
    }

    public Movimentacao findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Movimentacao m = em.find(Movimentacao.class, id);
        em.close();
        return m;
    }

    public Movimentacao findByDescricao(String nomeMovimentacao) {
        Movimentacao m = null;
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.descricao LIKE :nomeMovimentacao");
            query.setParameter("nomeMovimentacao", nomeMovimentacao);
            m = (Movimentacao) query.getSingleResult();
        } catch (NoResultException nre) {

        }
        em.close();
        return m;
    }

    public ArrayList<Movimentacao> getAll(Cliente c) {
        EntityManager em = emf.createEntityManager();
        ArrayList<Movimentacao> movimentacoes = null;
        try {
            Query query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.cliente.id = :idCliente");
            query.setParameter("idCliente", c.getId());
            movimentacoes = (ArrayList<Movimentacao>) query.getResultList();
        } catch (NoResultException nre) {

        }
        em.close();
        return movimentacoes;
    }

    public ArrayList<Movimentacao> getByDate(Cliente c, Date dataDe, Date dataAte) {
        EntityManager em = emf.createEntityManager();
        ArrayList<Movimentacao> movimentacoes = null;
        try {
            Query query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.cliente.id = :idCliente"
                    + " AND m.data BETWEEN :dataInicio AND :dataFim");
            query.setParameter("idCliente", c.getId());

            query.setParameter("dataInicio", dataDe, TemporalType.DATE);
            query.setParameter("dataFim", dataAte, TemporalType.DATE);

            movimentacoes = (ArrayList<Movimentacao>) query.getResultList();

        } catch (NoResultException nre) {

        }
        em.close();
        return movimentacoes;
    }

    public ArrayList<Movimentacao> getByTipo(Cliente c, Character tipoMovimentacao) {
        EntityManager em = emf.createEntityManager();
        ArrayList<Movimentacao> movimentacoes = null;
        try {
            Query query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.cliente.id = :idCliente"
                    + " AND m.tipo = :tipoMovimentacao");
            query.setParameter("idCliente", c.getId());
            query.setParameter("tipoMovimentacao", tipoMovimentacao);

            movimentacoes = (ArrayList<Movimentacao>) query.getResultList();
        } catch (NoResultException nre) {

        }
        em.close();
        return movimentacoes;
    }

    public ArrayList<Movimentacao> getByCategoria(Categoria categoria) {
        EntityManager em = emf.createEntityManager();
        ArrayList<Movimentacao> movimentacoes = null;
        try {
            Query query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.categoria.id = :idCategoria");
            query.setParameter("idCategoria", categoria.getIdCategoria());

            movimentacoes = (ArrayList<Movimentacao>) query.getResultList();
        } catch (NoResultException nre) {

        }
        em.close();
        return movimentacoes;
    }

    public ArrayList<Movimentacao> getByGrupo(Grupo grupo) {
        EntityManager em = emf.createEntityManager();
        ArrayList<Movimentacao> movimentacoes = null;
        try {
            Query query = em.createQuery("SELECT m FROM Movimentacao m WHERE m.grupo.id = :idGrupo");
            query.setParameter("idGrupo", grupo.getIdGrupo());

            movimentacoes = (ArrayList<Movimentacao>) query.getResultList();
        } catch (NoResultException nre) {

        }
        em.close();
        return movimentacoes;
    }
}
