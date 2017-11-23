package gerenciador.model;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Movimentacao implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String descricao;
   
    private Double valor;
    
    private Character tipo;
    
    @Temporal(TemporalType.DATE)
    private Calendar data;
    
    @ManyToOne
    private Grupo grupo;
    
    @ManyToOne
    private Categoria categoria;

    @ManyToOne
    private Cliente cliente;

    public Movimentacao() {
    }

    public Movimentacao(String nomeMovimentacao, Double valorMovimentacao, Character tipoMovimentacao, Calendar dataMovimentacao, Grupo grupoMovimentacao, Categoria categoriaMovimentacao, Cliente clienteMovimentacao) {
        this.descricao = nomeMovimentacao;
        this.valor = valorMovimentacao;
        this.tipo = tipoMovimentacao;
        this.data = dataMovimentacao;
        this.grupo = grupoMovimentacao;
        this.categoria = categoriaMovimentacao;
        this.cliente = clienteMovimentacao;
    }
        
    public Long getIdMovimentacao() {
        return id;
    }

    public void setIdMovimentacao(Long idMovimentacao) {
        this.id = idMovimentacao;
    }

    public String getDescricaoMovimentacao() {
        return descricao;
    }

    public void setDescricaoMovimentacao(String descricaoMovimentacao) {
        this.descricao = descricaoMovimentacao;
    }

    public Double getValorMovimentacao() {
        return valor;
    }

    public void setValorMovimentacao(Double valorMovimentacao) {
        this.valor = valorMovimentacao;
    }   
    
    public Character getTipoMovimentacao() {
        return tipo;
    }

    public void setTipoMovimentacao(Character tipoMovimentacao) {
        this.tipo = tipoMovimentacao;
    }

    public Calendar getDataMovimentacao() {
        return data;
    }

    public void setDataMovimentacao(Calendar dataMovimentacao) {
        this.data = dataMovimentacao;
    }

    public Grupo getGrupoMovimentacao() {
        return grupo;
    }

    public void setGrupoMovimentacao(Grupo grupoMovimentacao) {
        this.grupo = grupoMovimentacao;
    }

    public Categoria getCategoriaMovimentacao() {
        return categoria;
    }

    public void setCategoriaMovimentacao(Categoria categoriaMovimentacao) {
        this.categoria = categoriaMovimentacao;
    }

    public Cliente getClienteMovimentacao() {
        return cliente;
    }

    public void setClienteMovimentacao(Cliente clienteMovimentacao) {
        this.cliente = clienteMovimentacao;
    }
}
