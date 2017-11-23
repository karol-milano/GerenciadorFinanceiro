package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.MovimentacaoDao;
import gerenciador.model.Cliente;
import gerenciador.model.Movimentacao;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.util.Callback;

public class ListarMovimentacaoController implements Initializable {

    @FXML
    private ToggleGroup DespesaReceitaTG;

    @FXML
    private TableView<Movimentacao> tabelaMvt;

    @FXML
    private TableColumn<Movimentacao, String> colunaDataMvt;

    @FXML
    private TableColumn<Movimentacao, String> colunaCategoriaMvt;

    @FXML
    private TableColumn<Movimentacao, String> colunaGrupoMvt;

    @FXML
    private TableColumn<Movimentacao, String> colunaDescricaoMvt;

    @FXML
    private TableColumn<Movimentacao, Double> colunaValorMvt;

    private Cliente clienteLogado;
    private GerenciadorFinanceiroApp application;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colunaDataMvt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = formatter.format(param.getValue().getDataMovimentacao().getTime());
                return new SimpleObjectProperty<>(currentDate);
            }
        });

        colunaGrupoMvt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                if (param.getValue().getGrupoMovimentacao() != null) {
                    return new SimpleObjectProperty<>(param.getValue().getGrupoMovimentacao().getNomeGrupo());
                } else {
                    return new SimpleObjectProperty<>("");
                }
            }
        });

        colunaCategoriaMvt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getCategoriaMovimentacao().getNomeCategoria());
            }
        });

        colunaDescricaoMvt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getDescricaoMovimentacao());
            }
        });

        colunaValorMvt.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Movimentacao, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().getValorMovimentacao());
            }
        });

        atualizarTabelaMvt();
    }

    @FXML
    void cadastrarMvt(ActionEvent event) {
        boolean salvo = application.exibirTelaDeEditarMovimentacao(null);
        if (salvo) {
            atualizarTabelaMvt();
        }
    }

    @FXML
    void editarMvt(ActionEvent event) {
        Movimentacao movimentacao = tabelaMvt.getSelectionModel().getSelectedItem();
        if (movimentacao != null) {
            boolean salvo = application.exibirTelaDeEditarMovimentacao(movimentacao);
            if (salvo) {
                atualizarTabelaMvt();
            }
        }
    }

    @FXML
    void excluirMvt(ActionEvent event) {
        Movimentacao movimentacao = tabelaMvt.getSelectionModel().getSelectedItem();

        if (movimentacao != null) {
            MovimentacaoDao movimentacaoDao = new MovimentacaoDao();
            movimentacaoDao.excluirMovimentacao(movimentacao);

            int selectedIndex = tabelaMvt.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                tabelaMvt.getItems().remove(selectedIndex);
            }
        }
    }

    @FXML
    void recarregarTabelaMvt(ActionEvent event) {
        atualizarTabelaMvt();
    }

    public ListarMovimentacaoController(GerenciadorFinanceiroApp application, Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
        this.application = application;
    }

    public void atualizarTabelaMvt() {
        MovimentacaoDao movimentacaoDao = new MovimentacaoDao();

        Character tipoMovimentacao = 'd';
        RadioButton rb = (RadioButton) DespesaReceitaTG.getSelectedToggle();
        if (rb.getText().equals("Receita")) {
            tipoMovimentacao = 'r';
        }

        ArrayList<Movimentacao> movimentacoes = movimentacaoDao.getByTipo(clienteLogado, tipoMovimentacao);

        if (movimentacoes != null) {
            tabelaMvt.setItems(FXCollections.observableArrayList(movimentacoes));
        }
    }

    public void setClienteLogado(Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
    }

    public void setApplication(GerenciadorFinanceiroApp application) {
        this.application = application;
    }
}
