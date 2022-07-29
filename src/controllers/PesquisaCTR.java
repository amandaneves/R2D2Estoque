package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.jstage;

import java.io.IOException;

/**
 * Created by Amanda on 16/11/2016.
 */
public class PesquisaCTR extends jstage {

    @FXML
    private TextField txtPesquisa;

    @FXML
    private ListView<Object> lsvDados_pesquisa;

    @FXML
    private Button btnSelecionar;

    private ObservableList lista;
    private Object retorno;

    public PesquisaCTR(ObservableList lista) {
        try {
            this.OnCreate("/views/PesquisaFRM.fxml");

            this.lista = FXCollections.observableArrayList();
            this.lista = lista;
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

        lsvDados_pesquisa.setItems(lista);
        btnSelecionar.setDisable(true);

        lsvDados_pesquisa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnSelecionar.setDisable(newValue == null);
        });

        lsvDados_pesquisa.setOnMousePressed((event) -> {
            lsvDados_Pesquisa_OnMousePressed(event);
        });

        txtPesquisa.textProperty().addListener((observable, oldValue, newValue) -> {

        });

        lsvDados_pesquisa.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSelecionar.fire();
            }
        });
    }

    @FXML
    void lsvDados_Pesquisa_OnMousePressed(MouseEvent event) {
        if (event.getClickCount() > 1) {
            btnSelecionar.fire();
        }
    }

    @FXML
    void btnSelecionar_Click(ActionEvent event) {
        retorno = lsvDados_pesquisa.getItems().get(lsvDados_pesquisa.getSelectionModel().getSelectedIndex());
        ((Stage) btnSelecionar.getScene().getWindow()).close();
    }

    public Object getSelectedRow() {
        return retorno;
    }

}
