package gerenciador.controller;

import gerenciador.GerenciadorFinanceiroApp;
import gerenciador.dao.ClienteDao;
import gerenciador.model.Cliente;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;
    
    @FXML
    private TextField txtCadastrarNome;
    
    @FXML
    private TextField txtCadastrarUsuario;

    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private PasswordField txtCadastrarSenha;

    @FXML
    private Label labelError;

    private ClienteDao clienteDao;

    private GerenciadorFinanceiroApp application;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDao = new ClienteDao();
        labelError.setText("");
        txtUsuario.requestFocus();
    }

    @FXML
    public void acaoEntrar(ActionEvent event) {
        if (application != null) {

            String strUsuario = txtUsuario.getText();
            String strSenha = txtSenha.getText();

            if (!strUsuario.trim().isEmpty() && !strSenha.trim().isEmpty()) {
                Cliente cliente = clienteDao.findByUsuario(strUsuario);
                if (cliente != null) {
                    application.setClienteLogado(cliente);
                    application.exibirTelaPrincipal();
                    return;
                }
            }

            labelError.setText("Usuário/senha incorretos.");
        }
    }

    @FXML
    public void acaoCadastrar(ActionEvent event) {
        if (application != null) {
            String strCadastrarNome = txtCadastrarNome.getText();
            String strCadastrarUsuario = txtCadastrarUsuario.getText();
            String strCadastrarSenha = txtCadastrarSenha.getText();

            Cliente cliente = new Cliente();
            cliente.setNome(strCadastrarNome);
            cliente.setUsuario(strCadastrarUsuario);
            cliente.setSenha(strCadastrarSenha);

            cliente = clienteDao.cadastrarCliente(cliente);
            if (cliente != null) {
                application.setClienteLogado(cliente);
                application.exibirTelaPrincipal();
                return;
            }
        }
    }

    public void setApplication(GerenciadorFinanceiroApp application) {
        this.application = application;
    }
}
