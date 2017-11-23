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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class ListarCategoriaGrupoController implements Initializable {

    @FXML
    private ListView<Categoria> tabelaCategoria;

    @FXML
    private ListView<Grupo> tabelaGrupo;

    private Cliente clienteLogado;
    private GerenciadorFinanceiroApp application;

    public ListarCategoriaGrupoController(GerenciadorFinanceiroApp application, Cliente clienteLogado) {
        this.clienteLogado = clienteLogado;
        this.application = application;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabelaCategoria.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Categoria>(){
            @Override
            public void changed(ObservableValue<? extends Categoria> observable, Categoria oldValue, Categoria newValue) {
                atualizarTabelaGrupo();
            }
        });
        atualizarTabelaCategoria();
    }

    @FXML
    void cadastrarCategoria(ActionEvent event) {
        boolean salvo = application.exibirTelaDeEditarCategoria(null);
        if (salvo) {
            atualizarTabelaCategoria();
        }
    }

    @FXML
    void editarCategoria(ActionEvent event) {
        Categoria categoria = tabelaCategoria.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            boolean salvo = application.exibirTelaDeEditarCategoria(categoria);
            if (salvo) {
                atualizarTabelaCategoria();
            }
        }
    }

    @FXML
    void deletarCategoria(ActionEvent event) {
        Categoria categoria = tabelaCategoria.getSelectionModel().getSelectedItem();

        if (categoria != null) {

            MovimentacaoDao movimentacaoDao = new MovimentacaoDao();
            ArrayList<Movimentacao> movimentacoes = movimentacaoDao.getByCategoria(categoria);
            if (movimentacoes == null || movimentacoes.isEmpty()) {

                GrupoDao grupoDao = new GrupoDao();
                ArrayList<Grupo> grupos = grupoDao.getByCategoria(categoria);
                if (grupos == null || grupos.isEmpty()) {

                    CategoriaDao categoriaDao = new CategoriaDao();
                    categoriaDao.excluirCategoria(categoria);

                    int selectedIndex = tabelaCategoria.getSelectionModel().getSelectedIndex();
                    if (selectedIndex >= 0) {
                        tabelaCategoria.getItems().remove(selectedIndex);
                    }
                }
            }
        }
    }

    @FXML
    void cadastrarGrupo(ActionEvent event) {
        boolean salvo = application.exibirTelaDeEditarGrupo(null);
        if (salvo) {
            atualizarTabelaGrupo();
        }
    }

    @FXML
    void editarGrupo(ActionEvent event) {
        Grupo grupo = tabelaGrupo.getSelectionModel().getSelectedItem();
        if (grupo != null) {
            boolean salvo = application.exibirTelaDeEditarGrupo(grupo);
            if (salvo) {
                atualizarTabelaGrupo();
            }
        }
    }

    @FXML
    void deletarGrupo(ActionEvent event) {
        Grupo grupo = tabelaGrupo.getSelectionModel().getSelectedItem();

        if (grupo != null) {

            MovimentacaoDao movimentacaoDao = new MovimentacaoDao();
            ArrayList<Movimentacao> movimentacoes = movimentacaoDao.getByGrupo(grupo);
            if (movimentacoes == null || movimentacoes.isEmpty()) {

                GrupoDao grupoDao = new GrupoDao();
                grupoDao.excluirGrupo(grupo);

                int selectedIndex = tabelaGrupo.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    tabelaGrupo.getItems().remove(selectedIndex);
                }
            }
        }
    }

    public void atualizarTabelaCategoria() {
        CategoriaDao categoriaDao = new CategoriaDao();
        ArrayList<Categoria> categorias = categoriaDao.getAll();

        if (categorias != null) {
            tabelaCategoria.setItems(FXCollections.observableArrayList(categorias));
        }
    }

    public void atualizarTabelaGrupo() {
        Categoria categoria = tabelaCategoria.getSelectionModel().getSelectedItem();

        GrupoDao grupoDao = new GrupoDao();
        ArrayList<Grupo> grupos = grupoDao.getByCategoria(categoria);

        if (grupos != null) {
            tabelaGrupo.setItems(FXCollections.observableArrayList(grupos));
        }
    }
}
