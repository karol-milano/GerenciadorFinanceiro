package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.MovimentacaoDao;
import gerenciador.model.Cliente;
import gerenciador.model.Movimentacao;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class SituacaoFinanceiraController implements Initializable {

    @FXML
    private ChoiceBox<String> choiceMes;

    @FXML
    private TableView<Movimentacao> tableMovimentacao;

    @FXML
    private TableColumn<Movimentacao, String> colunatipo;

    @FXML
    private TableColumn<Movimentacao, String> colunaData;

    @FXML
    private TableColumn<Movimentacao, String> colunaCategoria;

    @FXML
    private TableColumn<Movimentacao, String> colunaGrupo;

    @FXML
    private TableColumn<Movimentacao, String> colunaDescricao;

    @FXML
    private TableColumn<Movimentacao, Double> colunaValor;

    @FXML
    private Label totalreceita;

    @FXML
    private Label totaldespesa;

    @FXML
    private Label receitadespesa;

    private Cliente clienteLogado;
    private GerenciadorFinanceiroApp application;

    public SituacaoFinanceiraController(GerenciadorFinanceiroApp application, Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colunatipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                String tipo = param.getValue().getTipoMovimentacao().toString();
                if (tipo.compareTo("d") == 0) {
                    tipo = "Despesa";
                } else {
                    tipo = "Receita";
                }
                return new SimpleObjectProperty<>(tipo);
            }
        });

        colunaData.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = formatter.format(param.getValue().getDataMovimentacao().getTime());
                return new SimpleObjectProperty<>(currentDate);
            }
        });

        colunaCategoria.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getCategoriaMovimentacao().getNomeCategoria());
            }
        });

        colunaGrupo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                if (param.getValue().getGrupoMovimentacao() != null) {
                    return new SimpleObjectProperty<>(param.getValue().getGrupoMovimentacao().getNomeGrupo());
                } else {
                    return new SimpleObjectProperty<>("");
                }
            }
        });

        colunaDescricao.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Movimentacao, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getDescricaoMovimentacao());
            }
        });

        colunaValor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Movimentacao, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Movimentacao, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().getValorMovimentacao());
            }
        });

        String value = "Selecione";
        int mes = Calendar.getInstance().get(Calendar.MONTH);
        switch (mes) {
            case 0:
                value = "Janeiro";
                break;
            case 1:
                value = "Feveiro";
                break;
            case 2:
                value = "Março";
                break;
            case 3:
                value = "Abril";
                break;
            case 4:
                value = "Maio";
                break;
            case 5:
                value = "Junho";
                break;
            case 6:
                value = "Julho";
                break;
            case 7:
                value = "Agosto";
                break;
            case 8:
                value = "Setembro";
                break;
            case 9:
                value = "Outubro";
                break;
            case 10:
                value = "Novembro";
                break;
            case 11:
                value = "Dezembro";
                break;
        }

        choiceMes.getSelectionModel().select(value);
        atualizarTabela(mes);
    }

    @FXML
    void atualizaDadosMes(ActionEvent event) {
        int mes = -1;

        switch (choiceMes.getValue()) {
            case "Janeiro":
                mes = 0;
                break;
            case "Feveiro":
                mes = 1;
                break;
            case "Março":
                mes = 2;
                break;
            case "Abril":
                mes = 3;
                break;
            case "Maio":
                mes = 4;
                break;
            case "Junho":
                mes = 5;
                break;
            case "Julho":
                mes = 6;
                break;
            case "Agosto":
                mes = 7;
                break;
            case "Setembro":
                mes = 8;
                break;
            case "Outubro":
                mes = 9;
                break;
            case "Novembro":
                mes = 10;
                break;
            case "Dezembro":
                mes = 11;
                break;
        }

        atualizarTabela(mes);
    }

    public void setClienteLogado(Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
    }

    public void setApplication(GerenciadorFinanceiroApp application) {
        this.application = application;
    }

    public void atualizarTabela(int mes) {
        LocalDate initial;
        MovimentacaoDao movimentacaoDao = new MovimentacaoDao();

        if (mes != -1) {
            initial = LocalDate.of(2017, mes, 01);
        } else {
            initial = LocalDate.now();
        }

        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());

        Date dataDe = Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataAte = Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        ArrayList<Movimentacao> movimentacoes = movimentacaoDao.getByDate(clienteLogado, dataDe, dataAte);

        if (movimentacoes != null) {

            Double despesa = 0.0;
            Double receita = 0.0;

            for (Movimentacao m : movimentacoes) {
                if (m.getTipoMovimentacao() == 'd') {
                    despesa += m.getValorMovimentacao();
                } else {
                    receita += m.getValorMovimentacao();
                }
            }

            totaldespesa.setText(despesa.toString());
            totalreceita.setText(receita.toString());

            receitadespesa.setText(String.valueOf(receita - despesa));

            tableMovimentacao.setItems(FXCollections.observableArrayList(movimentacoes));
        }
    }
}
