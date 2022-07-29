package controllers;

import application.R2D2Estoque;
import enums.Funcao;
import information.Categoria;
import information.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.WindowEvent;
import models.ProdutosRfidMDL;
import util.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Created by Amanda on 16/11/2016.
 */
public class ProdutoCTR extends jstage {

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
    private TextField txtRfid;

    @FXML
    private TextField txtDescricao;

    @FXML
    private ComboBox<Categoria> cbxCategoria;

    @FXML
    private TextField txtDt_inventario;

    @FXML
    private TextField txtPreco_custo;

    @FXML
    private TextField txtPreco_medio;

    @FXML
    private TextField txtPreco_venda;

    @FXML
    private Label lblEstoque_atual;

    private Produto produto;
    private ObservableList<Categoria> categorias;
    private ProdutosRfidMDL prodParaCadasto;
    private MODO modo = MODO.PADRAO;

    enum MODO {
        PADRAO,
        RFID
    }

    public ProdutoCTR() {
        Construtor();
    }

    public ProdutoCTR(MODO modo, ProdutosRfidMDL prodParaCadasto) {
        this.modo = modo;
        this.prodParaCadasto = prodParaCadasto;
        Construtor();
    }

    private void Construtor() {
        try {
            this.OnCreate("/views/ProdutoFRM.fxml");
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

        try {
            Mascaras.setMaxLength(txtRfid, 8);
            Mascaras.setMaxLength(txtDescricao, 55);

            categorias = FXCollections.observableArrayList();
            categorias = new Categoria().getAll();
            cbxCategoria.setItems(categorias);
            cbxCategoria.setVisibleRowCount(3);
            cbxCategoria.setPromptText("Selecione...");

            txtPreco_medio.setDisable(true);
            txtDt_inventario.setDisable(true);
            btnExcluir.setDisable(true);
            btnSalvar.setDisable(true);
            btnLimpar.setDisable(true);
            acpDados.setDisable(true);

            btnExcluir.setFocusTraversable(true);
            btnLimpar.setFocusTraversable(true);

            txtPreco_custo.textProperty().addListener((observable, oldValue, newValue) -> {
                if(produto.getModo() == Funcao.INCLUSAO && !newValue.isEmpty()){
                    txtPreco_medio.setText(newValue);
                }
            });

            if (modo == MODO.RFID) {
                btnExcluir.setVisible(false);
                btnNovo.setVisible(false);
                btnEditar.setVisible(false);
                txtRfid.setDisable(true);
                txtRfid.setText(prodParaCadasto.getCodigo());
                btnNovo.fire();
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @FXML
    void btnNovo_Click(ActionEvent event) {
        try {
            produto = new Produto();

            txtDt_inventario.setText(new Date().fromDBServer().toString());
            btnSalvar.setDisable(false);
            btnLimpar.setDisable(false);
            acpDados.setDisable(false);

            btnNovo.setDisable(true);
            btnEditar.setDisable(true);

            txtRfid.requestFocus();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @FXML
    void btnEditar_Click(ActionEvent event) {
        try {
            produto = new Produto();
            produto = (Produto) R2D2Estoque.mostraPesquisa(produto.getAll());
            produto.setModo(Funcao.ALTERACAO);

            txtRfid.setText(produto.getRfid());
            txtDescricao.setText(produto.getDescricao());
            cbxCategoria.getSelectionModel().select(produto.getCategoria());
            txtDt_inventario.setText(produto.getDt_inventario().toString());
            txtPreco_custo.setText(produto.getPr_custo().toString());
            txtPreco_medio.setText(produto.getPr_medio().toString());
            txtPreco_venda.setText(produto.getPr_venda().toString());

            btnSalvar.setDisable(false);
            btnExcluir.setDisable(false);
            btnLimpar.setDisable(false);
            acpDados.setDisable(false);

            btnNovo.setDisable(true);
            btnEditar.setDisable(true);
            txtRfid.setDisable(true);
            txtDt_inventario.setDisable(true);

            txtDescricao.requestFocus();

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
            alert.setContentText("Tem certeza que deseja excluir este Produto?");

            ButtonType buttonTypeSim = new ButtonType("Sim");
            ButtonType buttonTypeNao = new ButtonType("Não");

            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeSim) {
                produto.delete();
                btnLimpar.fire();
                new Alert(Alert.AlertType.CONFIRMATION, "Produto excluído com êxito!", ButtonType.OK).showAndWait();
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
        try {
            txtDt_inventario.setText(new Date().fromDBServer().toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        txtPreco_custo.clear();
        txtPreco_medio.clear();
        txtPreco_venda.clear();

        cbxCategoria.getSelectionModel().clearSelection();
        cbxCategoria.setPromptText("Selecione...");

        if (modo == MODO.PADRAO) {
            btnLimpar.setDisable(true);
            btnSalvar.setDisable(true);
            btnExcluir.setDisable(true);
            btnNovo.setDisable(false);
            btnEditar.setDisable(false);
            txtRfid.setDisable(false);
            txtRfid.clear();
            acpDados.setDisable(true);
        }

    }

    @FXML
    void btnSalvar_Click(ActionEvent event) {
        try {
            produto.setRfid(txtRfid.getText());
            produto.setDescricao(txtDescricao.getText());
            produto.setCategoria(cbxCategoria.getValue());
            produto.setDt_inventario(new Date(txtDt_inventario.getText()));
            produto.setPr_custo(txtPreco_custo.getText().isEmpty() ? new BigDecimal("0") : new BigDecimal(txtPreco_custo.getText()));
            produto.setPr_medio(txtPreco_medio.getText().isEmpty() ? new BigDecimal("0") : new BigDecimal(txtPreco_medio.getText()));
            produto.setPr_venda(txtPreco_venda.getText().isEmpty() ? new BigDecimal("0") : new BigDecimal(txtPreco_venda.getText()));
            produto.commit();

            new Alert(Alert.AlertType.CONFIRMATION, "Registro processado com êxito!", ButtonType.OK).showAndWait();

            if (modo == MODO.PADRAO) {
                btnLimpar.fire();
            } else {
                prodParaCadasto.setDescricao(produto.getDescricao());
                prodParaCadasto.setCadastrado("Sim");
                this.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

}
