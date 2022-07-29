package controllers;

import application.R2D2Estoque;
import enums.Funcao;
import information.Empresa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
 * Created by Amanda on 23/11/2016.
 */
public class EmpresaCTR extends jstage {

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
    private TextField txtCnpj;

    @FXML
    private TextField txtRazao_social;

    @FXML
    private TextField txtNome_fantasia;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtCidade;

    @FXML
    private ComboBox<String> cbxUf;

    @FXML
    private CheckBox ckxMatriz;

    private Empresa empresa;
    private ObservableList<String> ufs = FXCollections.observableArrayList("AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT",
            "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO");

    public EmpresaCTR(){
        try {
            this.OnCreate("/views/EmpresaFRM.fxml");
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

        Mascaras.setMaxLength(txtCnpj, 14);
        Mascaras.setMaxLength(txtRazao_social, 55);
        Mascaras.setMaxLength(txtNome_fantasia, 55);
        Mascaras.setMaxLength(txtEndereco, 80);
        Mascaras.setMaxLength(txtCidade, 55);
        cbxUf.setItems(ufs);
        cbxUf.getSelectionModel().select(12);

        btnExcluir.setDisable(true);
        btnSalvar.setDisable(true);
        btnLimpar.setDisable(true);
        acpDados.setDisable(true);

        btnExcluir.setFocusTraversable(true);
        btnLimpar.setFocusTraversable(true);

    }

    @FXML
    void btnNovo_Click(ActionEvent event) {
        try {
            empresa = new Empresa();

            btnSalvar.setDisable(false);
            btnLimpar.setDisable(false);
            acpDados.setDisable(false);

            ckxMatriz.setDisable(empresa.temMatrizCadastrada());
            btnNovo.setDisable(true);
            btnEditar.setDisable(true);

            txtCnpj.requestFocus();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditar_Click(ActionEvent event) {
        try {
            empresa = new Empresa();
            empresa = (Empresa) R2D2Estoque.mostraPesquisa(empresa.getAll());
            empresa.setModo(Funcao.ALTERACAO);

            txtCnpj.setText(empresa.getCnpj());
            txtRazao_social.setText(empresa.getRazao_social());
            txtNome_fantasia.setText(empresa.getNome_fantasia());
            ckxMatriz.setSelected(empresa.isMatriz());
            txtEndereco.setText(empresa.getEndereco());
            txtCidade.setText(empresa.getCidade());
            cbxUf.getSelectionModel().select(empresa.getUf());

            if(!empresa.isMatriz() && empresa.temMatrizCadastrada()){
                ckxMatriz.setDisable(true);
            }

            btnSalvar.setDisable(false);
            btnExcluir.setDisable(false);
            btnLimpar.setDisable(false);
            acpDados.setDisable(false);

            txtCnpj.setDisable(true);
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setContentText("Tem certeza que deseja excluir esta Empresa?");

            ButtonType buttonTypeSim = new ButtonType("Sim");
            ButtonType buttonTypeNao = new ButtonType("Não");

            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeSim) {
                empresa.delete();
                btnLimpar.fire();
                new Alert(Alert.AlertType.CONFIRMATION, "Empresa excluída com êxito!", ButtonType.OK).showAndWait();
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
        txtCnpj.clear();
        txtRazao_social.clear();
        txtNome_fantasia.clear();
        ckxMatriz.setSelected(false);
        txtEndereco.clear();
        txtCidade.clear();
        cbxUf.getSelectionModel().select(12);

        acpDados.setDisable(true);
        btnLimpar.setDisable(true);
        btnSalvar.setDisable(true);
        btnExcluir.setDisable(true);

        ckxMatriz.setDisable(false);
        txtCnpj.setDisable(false);
        btnNovo.setDisable(false);
        btnEditar.setDisable(false);
    }

    @FXML
    void btnSalvar_Click(ActionEvent event) {
        try {
            empresa.setCnpj(txtCnpj.getText());
            empresa.setRazao_social(txtRazao_social.getText());
            empresa.setNome_fantasia(txtNome_fantasia.getText());
            empresa.setMatriz(ckxMatriz.isSelected());
            empresa.setEndereco(txtEndereco.getText());
            empresa.setCidade(txtCidade.getText());
            empresa.setUf(cbxUf.getSelectionModel().getSelectedItem());
            empresa.commit();

            new Alert(Alert.AlertType.CONFIRMATION, "Registro processado com êxito!", ButtonType.OK).showAndWait();
            btnLimpar.fire();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }
}
