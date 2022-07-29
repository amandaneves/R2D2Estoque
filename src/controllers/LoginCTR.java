package controllers;

import data.UsuarioDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.WindowEvent;
import util.Mascaras;
import util.jstage;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Amanda on 13/11/2016.
 */
public class LoginCTR extends jstage {

    @FXML
    private TextField txtUsuario;

    @FXML
    private Label lblUsuario_erro;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Label lblSenha_erro;

    @FXML
    private Button btnEntrar;

    public LoginCTR() {
        try {
            this.OnCreate("/views/LoginFRM.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnCreate(String fxml) throws IOException {
        super.OnCreate(fxml);
    }

    @Override
    public void OnShow(WindowEvent e) {
        super.OnShow(e);

        Mascaras.setMaxLength(txtUsuario, 20);
        Mascaras.setMaxLength(txtSenha, 12);
    }

    @FXML
    void btnEntrar_Click(ActionEvent event) {
        try {
            if (txtUsuario.getText().trim().isEmpty()) {
                lblUsuario_erro.setText("** Informe o Usuário.");
                txtUsuario.requestFocus();
                return;
            } else if (!new UsuarioDAO().temEsseUsernameCadastrado(txtUsuario.getText())){
                lblUsuario_erro.setText("** Este usuário não existe.");
                txtUsuario.requestFocus();
                return;
            } else{
                lblUsuario_erro.setText("");
            }

            if(txtSenha.getText().trim().isEmpty()){
                lblSenha_erro.setText("** Informe a Senha.");
                txtSenha.requestFocus();
                return;
            } else if(!new UsuarioDAO().senhaConfere(txtUsuario.getText(), txtSenha.getText())){
                lblSenha_erro.setText("** Esta senha não confere com o usuário.");
                txtSenha.requestFocus();
                return;
            } else {
                lblSenha_erro.setText("");
            }

            PrincipalCTR frm = new PrincipalCTR();
            frm.setTitle("R2D2 Estoque");
            frm.setMaximized(true);
            frm.show();
            this.close();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnEntrar_Pressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER){
            btnEntrar.fire();
        }
    }

}