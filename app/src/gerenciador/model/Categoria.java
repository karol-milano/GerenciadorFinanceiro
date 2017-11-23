package gerenciador.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nome;

    private Character tipo;
    
    public Categoria() {
    }

    public Categoria(String nomeCategoria, Character tipoCategoria) {
        this.nome = nomeCategoria;
        this.tipo = tipoCategoria;
    }
    
    public Long getIdCategoria() {
        return id;
    }

    public void setIdCategoria(Long idCategoria) {
        this.id = idCategoria;
    }

    public String getNomeCategoria() {
        return nome;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nome = nomeCategoria;
    }

    public Character getTipoCategoria() {
        return tipo;
    }

    public void setTipoCategoria(Character tipoCategoria) {
        this.tipo = tipoCategoria;
    }

    @Override
    public String toString() {
        return nome;
    }
}
