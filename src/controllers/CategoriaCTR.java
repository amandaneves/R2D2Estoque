package controllers;

import application.R2D2Estoque;
import enums.Funcao;
import information.Categoria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import util.Mascaras;
import util.PesquisaException;
import util.RegistroIncompletoException;
import util.jstage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Amanda on 16/11/2016.
 */
public class CategoriaCTR extends jstage {

    @FXML
    private AnchorPane acpTopo;

    @FXML
    private Button btnNovo;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnExcluir;

    @FXML
    private Button btnLimpar;

    @FXML
    private AnchorPane acpDados;

    @FXML
    private TextField txtDescricao;

    private Categoria categoria;

    public CategoriaCTR() {
        try {
            this.OnCreate("/views/CategoriaFRM.fxml");
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

        Mascaras.setMaxLength(txtDescricao, 60);
        btnExcluir.setDisable(true);
        btnSalvar.setDisable(true);
        btnLimpar.setDisable(true);
        acpDados.setDisable(true);

        btnExcluir.setFocusTraversable(true);
        btnLimpar.setFocusTraversable(true);
    }

    @FXML
    void btnNovo_Click(ActionEvent event) {
        categoria = new Categoria();

        btnSalvar.setDisable(false);
        btnLimpar.setDisable(false);
        acpDados.setDisable(false);

        btnNovo.setDisable(true);
        btnEditar.setDisable(true);

        txtDescricao.requestFocus();
    }

    @FXML
    void btnEditar_Click(ActionEvent event) {
        try {
            categoria = new Categoria();
            categoria = (Categoria) R2D2Estoque.mostraPesquisa(categoria.getAll());
            categoria.setModo(Funcao.ALTERACAO);

            txtDescricao.setText(categoria.getDescricao());

            btnSalvar.setDisable(false);
            btnExcluir.setDisable(false);
            btnLimpar.setDisable(false);
            acpDados.setDisable(false);

            btnNovo.setDisable(true);
            btnEditar.setDisable(true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch(PesquisaException e){

        }
    }

    @FXML
    void btnExcluir_Click(ActionEvent event) {
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setContentText("Tem certeza que deseja excluir esta Categoria?");

            ButtonType buttonTypeSim = new ButtonType("Sim");
            ButtonType buttonTypeNao = new ButtonType("Não");

            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeSim) {
                categoria.delete();
                btnLimpar.fire();
                new Alert(AlertType.CONFIRMATION, "Categoria excluída com êxito!", ButtonType.OK).showAndWait();
            } else {
                return;
            }
        } catch (SQLException e){
            e.printStackTrace();
        } catch (RegistroIncompletoException e){
            e.mostraAlerta();
        }
    }

    @FXML
    void btnLimpar_Click(ActionEvent event) {
        txtDescricao.clear();

        acpDados.setDisable(true);
        btnLimpar.setDisable(true);
        btnSalvar.setDisable(true);
        btnExcluir.setDisable(true);

        btnNovo.setDisable(false);
        btnEditar.setDisable(false);
    }

    @FXML
    void btnSalvar_Click(ActionEvent event) {
        try {
            categoria.setDescricao(txtDescricao.getText());
            categoria.commit();

            new Alert(AlertType.CONFIRMATION, "Registro processado com êxito!", ButtonType.OK).showAndWait();
            btnLimpar.fire();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

}
