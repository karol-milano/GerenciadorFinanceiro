package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.CategoriaDao;
import gerenciador.dao.PerfilDeConsumoDao;
import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import gerenciador.model.PerfilDeConsumo;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class PerfilDeConsumoController implements Initializable {

    @FXML
    private ChoiceBox<Categoria> choiceCategoria;

    @FXML
    private ChoiceBox<String> choiceTipo;
    
    @FXML
    private DatePicker datePickerDe;
    
    @FXML
    private DatePicker datePickerAte;

    @FXML
    private BarChart<String, Double> barchartPerfilDeConsumo;
    
    @FXML
    private TableView<PerfilDeConsumo> tabelaPerfilDeConsumo;

    @FXML
    private TableColumn<PerfilDeConsumo, String> colunaCategoriaPerfil;
    
    @FXML
    private TableColumn<PerfilDeConsumo, Double> colunaValorPerfil;
    
    @FXML
    private TableColumn<PerfilDeConsumo, String> colunaPorcentagemPerfil;

    private final Cliente clienteLogado;
    private final GerenciadorFinanceiroApp application;

    public PerfilDeConsumoController(GerenciadorFinanceiroApp application, Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colunaCategoriaPerfil.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PerfilDeConsumo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PerfilDeConsumo, String> param) {
                return new SimpleObjectProperty<>(param.getValue().getNomeCategoria());
            }
        });

        colunaValorPerfil.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PerfilDeConsumo, Double>, ObservableValue<Double>>() {
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PerfilDeConsumo, Double> param) {
                return new SimpleObjectProperty<>(param.getValue().getValor());
            }
        });
        
        colunaPorcentagemPerfil.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PerfilDeConsumo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<PerfilDeConsumo, String> param) {
                return new SimpleObjectProperty<>(String.valueOf(Math.round(param.getValue().getPorcentagem())));
            }
        });
        
        LocalDate initial = LocalDate.now();
        LocalDate start = initial.withDayOfMonth(1);
        LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth());
        
        datePickerDe.setValue(start);
        datePickerAte.setValue(end);
        Date dataDe = Date.from(datePickerDe.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dataAte = Date.from(datePickerAte.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        
        PerfilDeConsumoDao perfilDeConsumoDao = new PerfilDeConsumoDao();
        ArrayList<PerfilDeConsumo> arrayList = perfilDeConsumoDao.getByDate(clienteLogado, dataDe, dataAte);
        
        carregarDados(arrayList);
        carregarCategorias();
    }
    
    @FXML
    void atualizaCategoria(ActionEvent event) {
        carregarCategorias();
    }
    
    @FXML
    void filtrarTabela(ActionEvent event) {
        String strTipo = choiceTipo.getSelectionModel().getSelectedItem();
        Categoria categoria = choiceCategoria.getSelectionModel().getSelectedItem();
        LocalDate dataDe = datePickerDe.getValue();
        LocalDate dataAte = datePickerAte.getValue();
        
        PerfilDeConsumoDao perfilDeConsumoDao = new PerfilDeConsumoDao();
        ArrayList<PerfilDeConsumo> arrayList = perfilDeConsumoDao.filter(clienteLogado, strTipo, categoria, dataDe, dataAte);
        
        carregarDados(arrayList);
    }
    
    public void carregarCategorias() {
        ArrayList<Categoria> categorias;
        CategoriaDao categoriaDao = new CategoriaDao();
        String strTipo = choiceTipo.getSelectionModel().getSelectedItem();
        if (strTipo.equals("Despesa")) {
            categorias = categoriaDao.getByTipo('d');
        }
        else if (strTipo.equals("Receita")) {
            categorias = categoriaDao.getByTipo('r');
        }
        else {
            categorias = categoriaDao.getAll();
        }
        
        choiceCategoria.setItems(FXCollections.observableArrayList(categorias));
    }
    
    public void carregarDados(ArrayList<PerfilDeConsumo> arrayList) {
        barchartPerfilDeConsumo.getData().clear();
        tabelaPerfilDeConsumo.getItems().clear();
        
        if (!arrayList.isEmpty()) {
            XYChart.Series<String, Double> series = new XYChart.Series<>();
            
            for(PerfilDeConsumo p: arrayList) {
                series.getData().add(new XYChart.Data<>(p.getNomeCategoria(), p.getPorcentagem()));
            }
            
            barchartPerfilDeConsumo.getData().add(series);
            tabelaPerfilDeConsumo.setItems(FXCollections.observableArrayList(arrayList));
        }
    }
}
