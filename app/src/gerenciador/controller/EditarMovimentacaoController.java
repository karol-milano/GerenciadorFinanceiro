package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.CategoriaDao;
import gerenciador.dao.GrupoDao;
import gerenciador.dao.MovimentacaoDao;
import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import gerenciador.model.Grupo;
import gerenciador.model.Movimentacao;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class EditarMovimentacaoController implements Initializable {

    @FXML
    private ChoiceBox<Categoria> choiceCategoria;

    @FXML
    private ChoiceBox<Grupo> choiceGrupo;

    @FXML
    private ToggleGroup receitaDespesa;

    @FXML
    private DatePicker dataMvt;

    @FXML
    private TextField valorMvt;

    @FXML
    private RadioButton checDespesa;

    @FXML
    private RadioButton checReceita;

    @FXML
    private TextField txtDescricaoMvt;

    private Stage dialogStage;
    private boolean salvo = false;
    private Cliente clienteLogado;
    private Movimentacao movimentacao;
    private GerenciadorFinanceiroApp application;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void atualizaCategoriaDespesa(ActionEvent event) {
        CategoriaDao categoriaDao = new CategoriaDao();
        ArrayList<Categoria> categorias = categoriaDao.getByTipo('d');
        choiceCategoria.setItems(FXCollections.observableArrayList(categorias));
        
        choiceGrupo.setDisable(true);
    }

    @FXML
    void atualizaCategoriaReceita(ActionEvent event) {
        CategoriaDao categoriaDao = new CategoriaDao();
        ArrayList<Categoria> categorias = categoriaDao.getByTipo('r');
        choiceCategoria.setItems(FXCollections.observableArrayList(categorias));
        
        choiceGrupo.setDisable(true);
    }

    @FXML
    void atualizaGrupo(ActionEvent event) {
        GrupoDao grupoDao = new GrupoDao();
        ArrayList<Grupo> grupos = grupoDao.getByCategoria(choiceCategoria.getValue());
        choiceGrupo.setItems(FXCollections.observableArrayList(grupos));
        
        choiceGrupo.setDisable(false);
    }
    
    @FXML
    void cancelarMvt(ActionEvent event) {
        dialogStage.close();
    }

    @FXML
    void salvarMvt(ActionEvent event) {

        Categoria categoria = choiceCategoria.getValue();
        Grupo grupo = choiceGrupo.getValue();

        Double valor = Double.parseDouble(valorMvt.getText());
        
        RadioButton rb = (RadioButton) receitaDespesa.getSelectedToggle();
        Character tipoMovimentacao = 'd';
        
        if (rb.getText().equals("Receita")) {
            tipoMovimentacao = 'r';
        }
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.from(dataMvt.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()));
    
        MovimentacaoDao movimentacaoDao = new MovimentacaoDao();
        Movimentacao mvt = new Movimentacao(txtDescricaoMvt.getText(), valor, tipoMovimentacao, calendar, grupo, categoria, clienteLogado);
        movimentacaoDao.cadastrarMovimentacao(mvt);
        
        salvo = true;
        dialogStage.close();
    }

    public boolean isSalvo() {
        return salvo;
    }

    public void setApplication(GerenciadorFinanceiroApp application) {
        this.application = application;
    }

    public void setMovimentacao(Movimentacao movimentacao) {
        if (movimentacao != null) {
            this.movimentacao = movimentacao;
            carregarMovimentacao();
        } else {
            this.movimentacao = new Movimentacao();
            carregarMovimentacaoVazia();
        }
    }

    public void carregarMovimentacao() {
        CategoriaDao categoriaDao = new CategoriaDao();
        ArrayList<Categoria> categorias = categoriaDao.getByTipo(movimentacao.getTipoMovimentacao());
        choiceCategoria.setItems(FXCollections.observableArrayList(categorias));
        choiceCategoria.getSelectionModel().select(movimentacao.getCategoriaMovimentacao());        

        GrupoDao grupoDao = new GrupoDao();
        ArrayList<Grupo> grupos = grupoDao.getByCategoria(movimentacao.getCategoriaMovimentacao());
        choiceGrupo.setItems(FXCollections.observableArrayList(grupos));
        choiceGrupo.getSelectionModel().select(movimentacao.getGrupoMovimentacao());
        
        txtDescricaoMvt.setText(movimentacao.getDescricaoMovimentacao());
        valorMvt.setText(movimentacao.getValorMovimentacao().toString());

        if (movimentacao.getTipoMovimentacao() == 'r') {
            checReceita.setSelected(true);
        } else if (movimentacao.getTipoMovimentacao() == 'd') {
            checDespesa.setSelected(true);
        }

        Date data = movimentacao.getDataMovimentacao().getTime();
        dataMvt.setValue(data.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
    
    public void carregarMovimentacaoVazia() {
        CategoriaDao categoriaDao = new CategoriaDao();
        ArrayList<Categoria> categorias = categoriaDao.getByTipo('d');
        choiceCategoria.setItems(FXCollections.observableArrayList(categorias));

        choiceGrupo.setDisable(true);

        txtDescricaoMvt.setText("");
        valorMvt.setText("");
        checDespesa.setSelected(true);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setClienteLogado(Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
    }
}
