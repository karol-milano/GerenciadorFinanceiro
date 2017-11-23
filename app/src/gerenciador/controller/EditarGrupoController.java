package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.CategoriaDao;
import gerenciador.dao.GrupoDao;
import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import gerenciador.model.Grupo;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarGrupoController implements Initializable {

    @FXML
    private ChoiceBox<Categoria> choiceCategoria;

    @FXML
    private TextField txtDescricaoGrupo;

    private Stage dialogStage;
    private boolean salvo = false;
    private Cliente clienteLogado;
    private Grupo grupo;
    private GerenciadorFinanceiroApp application;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void cancelarGrupo(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void salvarGrupo(ActionEvent event) {
        Categoria categoria = choiceCategoria.getValue();

        GrupoDao grupoDao = new GrupoDao();
        Grupo grp = new Grupo(txtDescricaoGrupo.getText(), categoria);
        grupoDao.cadastrarGrupo(grp);

        salvo = true;
        dialogStage.close();
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

    public void setGrupo(Grupo grupo) {
        CategoriaDao categoriaDao = new CategoriaDao();
        ArrayList<Categoria> categorias = categoriaDao.getAll();
        choiceCategoria.setItems(FXCollections.observableArrayList(categorias));
        
        if (grupo != null) {
            this.grupo = grupo;
            choiceCategoria.getSelectionModel().select(grupo.getCategoriaGrupo());
            txtDescricaoGrupo.setText(grupo.getNomeGrupo());
        } else {
            this.grupo = new Grupo();
            txtDescricaoGrupo.setText("");
        }
    }

    public void setApplication(GerenciadorFinanceiroApp application) {
        this.application = application;
    }
}
