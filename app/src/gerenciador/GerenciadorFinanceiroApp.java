package gerenciador;

import gerenciador.controller.EditarCategoriaController;
import gerenciador.controller.EditarGrupoController;
import gerenciador.controller.EditarMovimentacaoController;
import gerenciador.controller.InicialController;
import gerenciador.controller.LoginController;
import gerenciador.dao.CategoriaDao;
import gerenciador.dao.ClienteDao;
import gerenciador.dao.GrupoDao;
import gerenciador.dao.MovimentacaoDao;
import gerenciador.model.Categoria;
import gerenciador.model.Cliente;
import gerenciador.model.Grupo;
import gerenciador.model.Movimentacao;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class GerenciadorFinanceiroApp extends Application {

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        launch(args);
    }

    private Stage primaryStage;
    private Cliente clienteLogado;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.clienteLogado = null;
        this.primaryStage = primaryStage;
        exibirTelaDeLogin();
    }
    
    public void setClienteLogado(Cliente cliente) {
        clienteLogado = cliente;
    }

    public void exibirTelaDeLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(GerenciadorFinanceiroApp.class.getResource("/gerenciador/view/LoginLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("KV Manager");
            primaryStage.setMaximized(false);
            primaryStage.show();

            LoginController loginController = (LoginController) loader.getController();
            loginController.setApplication(this);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(GerenciadorFinanceiroApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void exibirTelaPrincipal() {
        try {
            Callback<Class<?>, Object> controllerFactory = new Callback<Class<?>, Object>() {
                @Override
                public Object call(Class<?> type) {
                    try {
                        for (Constructor<?> c : type.getConstructors()) {
                            if (c.getParameterCount() == 2
                                    && c.getParameterTypes()[0] == GerenciadorFinanceiroApp.class
                                    && c.getParameterTypes()[1] == Cliente.class) {
                                return c.newInstance(GerenciadorFinanceiroApp.this, clienteLogado);
                            }
                        }
                        // no suitable constructor found, just use default:
                        return type.newInstance();
                    } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | SecurityException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            };

            FXMLLoader loader = new FXMLLoader(GerenciadorFinanceiroApp.class.getResource("/gerenciador/view/InicialLayout.fxml"));
            loader.setControllerFactory(controllerFactory);
            BorderPane page = (BorderPane) loader.load();

            InicialController inicialController = (InicialController) loader.getController();
            inicialController.init();
            
            Scene scene = new Scene(page);
            primaryStage.setScene(scene);
            primaryStage.setTitle("KV Manager");
            primaryStage.setMaximized(true);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(GerenciadorFinanceiroApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean exibirTelaDeEditarMovimentacao(Movimentacao movimentacao) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GerenciadorFinanceiroApp.class.getResource("/gerenciador/view/EditarMovimentacaoLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (movimentacao != null) {
                dialogStage.setTitle("Editar Movimentação");
            } else {
                dialogStage.setTitle("Adicionar Movimentação");
            }

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditarMovimentacaoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMovimentacao(movimentacao);
            controller.setClienteLogado(clienteLogado);

            dialogStage.showAndWait();

            return controller.isSalvo();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exibirTelaDeEditarCategoria(Categoria categoria) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GerenciadorFinanceiroApp.class.getResource("/gerenciador/view/EditarCategoriaLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (categoria != null) {
                dialogStage.setTitle("Editar Categoria");
            } else {
                dialogStage.setTitle("Adicionar Categoria");
            }

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditarCategoriaController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setCategoria(categoria);
            controller.setClienteLogado(clienteLogado);

            dialogStage.showAndWait();

            return controller.isSalvo();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean exibirTelaDeEditarGrupo(Grupo grupo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(GerenciadorFinanceiroApp.class.getResource("/gerenciador/view/EditarGrupoLayout.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (grupo != null) {
                dialogStage.setTitle("Editar Grupo");
            } else {
                dialogStage.setTitle("Adicionar grupo");
            }

            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EditarGrupoController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setGrupo(grupo);
            controller.setClienteLogado(clienteLogado);

            dialogStage.showAndWait();

            return controller.isSalvo();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
