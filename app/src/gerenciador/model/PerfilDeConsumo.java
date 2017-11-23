package gerenciador.model;

public class PerfilDeConsumo {
    
    private String nomeCategoria;
    private Double valor;
    private Double porcentagem;

    public PerfilDeConsumo() {
    }

    public PerfilDeConsumo(String nomeCategoria, Double valor, Double porcentagem) {
        this.nomeCategoria = nomeCategoria;
        this.valor = valor;
        this.porcentagem = porcentagem;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getPorcentagem() {
        return porcentagem;
    }

    public void setPorcentagem(Double porcentagem) {
        this.porcentagem = porcentagem;
    }
}
