package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.CategoriaDao;
import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class EditarCategoriaController implements Initializable {

    @FXML
    private ToggleGroup receitaDespesa;

    @FXML
    private RadioButton checDespesa;

    @FXML
    private RadioButton checReceita;

    @FXML
    private TextField txtDescricaoCategoria;
    
    private Stage dialogStage;
    private boolean salvo = false;
    private Cliente clienteLogado;
    private Categoria categoria;
    private GerenciadorFinanceiroApp application;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
    @FXML
    void cancelarCategoria(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void salvarCategoria(ActionEvent event) {
        
        RadioButton rb = (RadioButton) receitaDespesa.getSelectedToggle();
        Character tipoMovimentacao = 'd';
        
        if (rb.getText().equals("Receita")) {
            tipoMovimentacao = 'r';
        }
        
        CategoriaDao categoriaDao = new CategoriaDao();
        Categoria cat = new Categoria(txtDescricaoCategoria.getText().toString(), tipoMovimentacao);
        categoriaDao.cadastrarCategoria(cat);
        
        salvo = true;
        dialogStage.close();
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public boolean isSalvo() {
        return salvo;
    }

    public void setSalvo(boolean salvo) {
        this.salvo = salvo;
    }

    public void setClienteLogado(Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
    }
    
    public void setCategoria(Categoria categoria) {
        if (categoria != null) {
            this.categoria = categoria;
            txtDescricaoCategoria.setText(categoria.getNomeCategoria());
        }
        else {
            this.categoria = new Categoria();
            txtDescricaoCategoria.setText("");
        }
    }

    public void setApplication(GerenciadorFinanceiroApp application) {
        this.application = application;
    }
    
    
}
