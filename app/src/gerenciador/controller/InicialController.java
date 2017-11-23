package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.model.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class InicialController implements Initializable {

    private Cliente clienteLogado;
    private GerenciadorFinanceiroApp application;
    
    @FXML
    private Label txtBemVindo;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private Tab tabCategorias;

    @FXML
    private Tab tabSituacaoFinanceira;

    @FXML
    private Tab tabMovimentacoes;

    @FXML
    private Tab tabPerfilDeConsumo;
    
    @FXML
    SituacaoFinanceiraController situacaoFinanceiraController;
    
    @FXML
    PerfilDeConsumoController perfilDeConsumoController;
            
    @FXML
    ListarMovimentacaoController listarMovimentacaoController;
    
    @FXML
    ListarCategoriaGrupoController listarCategoriaGrupoController;
    
    public InicialController(GerenciadorFinanceiroApp application, Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
        this.application = application;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txtBemVindo.setText("Bem vindo(a), " + clienteLogado.getNome());
    }
    
    public void init() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == tabSituacaoFinanceira) {
                situacaoFinanceiraController.atualizarTabela(-1);
            }
        });
    }
}
