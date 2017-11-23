package gerenciador.dao;

import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import gerenciador.model.PerfilDeConsumo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TemporalType;

public class PerfilDeConsumoDao {

    protected EntityManagerFactory emf;

    public PerfilDeConsumoDao() {
        emf = JPAUtil.getInstance().getEntityManagerFactory();
    }

    public ArrayList<PerfilDeConsumo> getByDate(Cliente c, Date dataDe, Date dataAte) {
        ArrayList<PerfilDeConsumo> arrayList = null;
        EntityManager em = emf.createEntityManager();

        try {
            String strQuery = "SELECT NEW gerenciador.model.PerfilDeConsumo(c.nome, SUM(m.valor), 0.0)"
                    + " FROM Movimentacao m"
                    + " LEFT JOIN m.categoria c"
                    + " WHERE m.cliente.id = :idCliente"
                    + " AND m.data BETWEEN :dataInicio AND :dataFim"
                    + " GROUP BY c.nome";
            
            Query query = em.createQuery(strQuery);
            query.setParameter("idCliente", c.getId());
            query.setParameter("dataInicio", dataDe, TemporalType.DATE);
            query.setParameter("dataFim", dataAte, TemporalType.DATE);
            arrayList = (ArrayList<PerfilDeConsumo>) query.getResultList();

            if (arrayList != null) {
                Double total = 0.0;
                for (PerfilDeConsumo p : arrayList) {
                    total += p.getValor();
                }

                for (PerfilDeConsumo p : arrayList) {
                    p.setPorcentagem((100.0 * p.getValor()) / total);
                }
            }

        } catch (NoResultException nre) {

        }

        em.close();
        return arrayList;
    }

    public ArrayList<PerfilDeConsumo> filter(Cliente clienteLogado, String strTipo, Categoria categoria, LocalDate dataDe, LocalDate dataAte) {
        ArrayList<PerfilDeConsumo> arrayList = null;
        EntityManager em = emf.createEntityManager();

        try {
            String strQuery = "SELECT NEW gerenciador.model.PerfilDeConsumo(c.nome, SUM(m.valor), 0.0)"
                    + " FROM Movimentacao m"
                    + " LEFT JOIN m.categoria c"
                    + " WHERE m.cliente.id = " + clienteLogado.getId();
                    
            if (categoria != null) {
                strQuery += " AND m.categoria.id = " + categoria.getIdCategoria();
            }
            
            if (strTipo != null && !strTipo.isEmpty()) {
                if (strTipo.equals("Despesa")) {
                    strQuery += " AND m.tipo = 'd'";
                } else if (strTipo.equals("Receita")) {
                    strQuery += " AND m.tipo = 'r'";
                }
            }
            
            if (dataDe != null && dataAte != null) {
                strQuery += " AND m.data BETWEEN '" + dataDe.toString() + "' AND '" + dataAte.toString() + "'";
            }
            
            strQuery += " GROUP BY c.nome";
            Query query = em.createQuery(strQuery);
            arrayList = (ArrayList<PerfilDeConsumo>) query.getResultList();

            if (arrayList != null) {
                Double total = 0.0;
                for (PerfilDeConsumo p : arrayList) {
                    total += p.getValor();
                }

                for (PerfilDeConsumo p : arrayList) {
                    p.setPorcentagem((100.0 * p.getValor()) / total);
                }
            }

        } catch (NoResultException nre) {

        }

        em.close();
        return arrayList;
    }
}
