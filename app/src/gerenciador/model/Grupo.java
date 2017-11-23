package gerenciador.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Grupo implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nome;
   
    @ManyToOne
    private Categoria categoria;

    public Grupo() {
    }

    public Grupo(String nomeGrupo, Categoria categoriaGrupo) {
        this.nome = nomeGrupo;
        this.categoria = categoriaGrupo;
    }
    
    public Long getIdGrupo() {
        return id;
    }

    public void setIdGrupo(Long idGrupo) {
        this.id = idGrupo;
    }

    public String getNomeGrupo() {
        return nome;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nome = nomeGrupo;
    }

    public Categoria getCategoriaGrupo() {
        return categoria;
    }

    public void setCategoriaGrupo(Categoria categoriaGrupo) {
        this.categoria = categoriaGrupo;
    }

    @Override
    public String toString() {
        return nome;
    }
}
