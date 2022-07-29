package controllers;

import data.EmpresaDAO;
import data.ProdutoDAO;
import information.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import models.ProdutosEntradaMDL;
import models.ProdutosRfidMDL;
import util.Date;
import util.RegistroIncompletoException;
import util.RfidImportacao;
import util.jstage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by Amanda on 17/11/2016.
 */
public class EntradaProdutosCTR extends jstage {

    @FXML
    private AnchorPane acpDados;

    @FXML
    private Label lblStatus_arquivo;

    @FXML
    private TableView<ProdutosRfidMDL> tbvProdutos_encontrados;

    @FXML
    private TableColumn<ProdutosRfidMDL, String> clnProduto_encontrado_id;

    @FXML
    private TableColumn<ProdutosRfidMDL, String> clnProduto_encontrado_descricao;

    @FXML
    private TableColumn<ProdutosRfidMDL, String> clnCadastrado;

    @FXML
    private Button btnAdicionar_entrada;

    @FXML
    private Button btnCopiar_um;

    @FXML
    private Button btnCopiar_todos;

    @FXML
    private TextField txtPreco_custo;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private ComboBox<Empresa> cbxEmpresa;

    @FXML
    private TableView<ProdutosEntradaMDL> tbvProdutos_entrada;

    @FXML
    private TableColumn<ProdutosEntradaMDL, String> clnProduto_entrada_id;

    @FXML
    private TableColumn<ProdutosEntradaMDL, String> clnProduto_entrada_descricao;

    @FXML
    private TableColumn<ProdutosEntradaMDL, String> clnProduto_entrada_quantidade;

    @FXML
    private TableColumn<ProdutosEntradaMDL, String> clnPreco_custo;

    @FXML
    private Button btnConfirmar;

    @FXML
    private Button btnVoltar_um;

    @FXML
    private Button btnVoltar_todos;

    private ObservableList<ProdutosRfidMDL> produtosRfid;
    private ObservableList<ProdutosEntradaMDL> produtosEntrada;
    private ObservableList<Empresa> empresas;
    private Rfid rfid;
    private Entrada entrada;
    private EntradaProdutos entradaProdutos;
    private ProdutosEntradaMDL produtoEntrada;

    public EntradaProdutosCTR() {
        try {
            this.OnCreate("/views/EntradaProdutosFRM.fxml");
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
            produtosRfid = FXCollections.observableArrayList();
            produtosEntrada = FXCollections.observableArrayList();
            empresas = FXCollections.observableArrayList();

            clnProduto_encontrado_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            clnProduto_encontrado_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            clnCadastrado.setCellValueFactory(new PropertyValueFactory<>("cadastrado"));
            tbvProdutos_encontrados.setItems(produtosRfid);

            clnProduto_entrada_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            clnProduto_entrada_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            clnProduto_entrada_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            clnPreco_custo.setCellValueFactory(new PropertyValueFactory<>("pr_custo"));
            tbvProdutos_entrada.setItems(produtosEntrada);

            empresas.setAll(new EmpresaDAO().getAll());
            cbxEmpresa.setItems(empresas);
            cbxEmpresa.setPromptText("Selecione...");

            btnCopiar_um.setDisable(true);
            btnCopiar_todos.setDisable(true);
            btnVoltar_um.setDisable(true);
            btnVoltar_todos.setDisable(true);
            txtPreco_custo.setDisable(true);
            txtQuantidade.setDisable(true);
            btnAdicionar_entrada.setDisable(true);

            tbvProdutos_encontrados.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                btnCopiar_um.setDisable(newValue.getCadastrado().equals("Não"));
                btnVoltar_um.setDisable(newValue.getCadastrado().equals("Não"));
                btnCopiar_todos.setDisable(newValue.getCadastrado().equals("Não"));
                btnVoltar_todos.setDisable(newValue.getCadastrado().equals("Não"));
            });

            rfid = new Rfid();
            if (rfid.get() != null) {
                if (!rfid.get().exists()) {
                    lblStatus_arquivo.setText("Arquivo não encontrado!");
                    lblStatus_arquivo.setTextFill(Color.RED);
                } else {
                    lblStatus_arquivo.setText("Arquivo encontrado.");
                    lblStatus_arquivo.setTextFill(Color.GREEN);
                    rfid.setPath(rfid.get().getPath());
                    processaArquivo(new File(rfid.getPath()));
                }
            } else {
                lblStatus_arquivo.setText("Configure o arquivo RFID.");
                lblStatus_arquivo.setTextFill(Color.BLUE);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    private void processaArquivo(File file) throws Exception {
        if (file != null) {
            //COMEÇANDO A IMPORTAÇÃO DO ARQUIVO.
            RfidImportacao importacao = new RfidImportacao();

            if (importacao.importar(rfid) == 0) {
                lblStatus_arquivo.setText("Nenhum produto importado!");
                lblStatus_arquivo.setTextFill(Color.RED);
                return;
            }

            //PREENCHENDO O TABLEVIEW.
            produtosRfid.addAll(importacao.getProdutos());
        }
    }

    @FXML
    void btnConfirmar_Click(ActionEvent event) {
        try {
            entrada = new Entrada();
            entrada.setData(new Date().fromDBServer());
            if (cbxEmpresa.getSelectionModel().getSelectedItem() != null)
                entrada.setEmpresa(cbxEmpresa.getValue());

            for (int i = 0; i < produtosEntrada.size(); i++) {
                entradaProdutos = new EntradaProdutos();
                entradaProdutos.setProduto(new ProdutoDAO().get(produtosEntrada.get(i).getCodigo()));
                entradaProdutos.setPr_custo(new BigDecimal(produtosEntrada.get(i).getPr_custo()));
                entradaProdutos.setQuantidade(new BigDecimal(produtosEntrada.get(i).getQuantidade()));
                entrada.addEntradaProdutos(entradaProdutos);
            }

            entrada.commit();

            new Alert(Alert.AlertType.CONFIRMATION, "Registros processados com êxito!", ButtonType.OK).showAndWait();
            produtosEntrada.clear();
            cbxEmpresa.getSelectionModel().clearSelection();
            cbxEmpresa.setPromptText("Selecione...");
            limparCampos();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

    @FXML
    void btnCopiar_todos_Click(ActionEvent event) {
        try {
            for (int i = 0; i < produtosRfid.size(); i++) {
                if (produtosRfid.get(i).getCadastrado().equals("Sim")) {
                    String rfid = produtosRfid.get(i).getCodigo();
                    produtoEntrada = new ProdutosEntradaMDL(rfid, new ProdutoDAO().get(rfid).getDescricao(), "1", new ProdutoDAO().get(rfid).getPr_custo() + "");
                    produtosEntrada.add(produtoEntrada);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnCopiar_um_Click(ActionEvent event) {
        try {
            if (tbvProdutos_encontrados.getSelectionModel().getSelectedItem() != null) {
                txtPreco_custo.setDisable(false);
                txtQuantidade.setDisable(false);
                btnAdicionar_entrada.setDisable(false);
                tbvProdutos_encontrados.setDisable(true);
                txtPreco_custo.setText(new ProdutoDAO().get(tbvProdutos_encontrados.getSelectionModel().getSelectedItem().getCodigo()).getPr_custo() + "");
                txtQuantidade.setText("1.00");
                txtPreco_custo.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnVoltar_todos_Click(ActionEvent event) {
        produtosEntrada.clear();
    }

    @FXML
    void btnVoltar_um_Click(ActionEvent event) {
        if (tbvProdutos_entrada.getSelectionModel().getSelectedItem() != null) {
            produtosEntrada.remove(tbvProdutos_entrada.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    void btnAdicionar_entrada_Click(ActionEvent event) {
        try {

            String rfid = tbvProdutos_encontrados.getSelectionModel().getSelectedItem().getCodigo();
            produtoEntrada = new ProdutosEntradaMDL(rfid, new ProdutoDAO().get(rfid).getDescricao(), txtQuantidade.getText(), txtPreco_custo.getText());
            produtoEntrada.valida();
            produtosEntrada.add(produtoEntrada);

            limparCampos();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

    private void limparCampos(){
        tbvProdutos_encontrados.setDisable(false);
        txtPreco_custo.setDisable(true);
        txtQuantidade.setDisable(true);
        btnAdicionar_entrada.setDisable(true);

        txtPreco_custo.clear();
        txtQuantidade.clear();
    }

}
