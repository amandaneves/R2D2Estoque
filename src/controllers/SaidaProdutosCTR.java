package controllers;

import data.EmpresaDAO;
import data.ProdutoDAO;
import information.Empresa;
import information.Rfid;
import information.Saida;
import information.SaidaProdutos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import models.ProdutosRfidMDL;
import models.ProdutosSaidaMDL;
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
 * Created by Amanda on 23/11/2016.
 */
public class SaidaProdutosCTR extends jstage {

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
    private Button btnCopiar_um;

    @FXML
    private Button btnCopiar_todos;

    @FXML
    private Button btnVoltar_um;

    @FXML
    private Button btnVoltar_todos;

    @FXML
    private TextField txtPreco_venda;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private ComboBox<Empresa> cbxEmpresa;

    @FXML
    private Button btnAdicionar_saida;

    @FXML
    private TableView<ProdutosSaidaMDL> tbvProdutos_saida;

    @FXML
    private TableColumn<ProdutosSaidaMDL, String> clnProduto_saida_id;

    @FXML
    private TableColumn<ProdutosSaidaMDL, String> clnProduto_saida_descricao;

    @FXML
    private TableColumn<ProdutosSaidaMDL, String> clnProduto_saida_quantidade;

    @FXML
    private TableColumn<ProdutosSaidaMDL, String> clnPreco_venda;

    @FXML
    private Button btnConfirmar;

    private ObservableList<ProdutosRfidMDL> produtosRfid;
    private ObservableList<ProdutosSaidaMDL> produtosSaida;
    private ObservableList<Empresa> empresas;
    private Rfid rfid;
    private Saida saida;
    private SaidaProdutos saidaProdutos;
    private ProdutosSaidaMDL produtoSaida;

    public SaidaProdutosCTR() {
        try {
            this.OnCreate("/views/SaidaProdutosFRM.fxml");
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
            produtosSaida = FXCollections.observableArrayList();
            empresas = FXCollections.observableArrayList();

            clnProduto_encontrado_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            clnProduto_encontrado_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            clnCadastrado.setCellValueFactory(new PropertyValueFactory<>("cadastrado"));
            tbvProdutos_encontrados.setItems(produtosRfid);

            clnProduto_saida_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
            clnProduto_saida_descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            clnProduto_saida_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
            clnPreco_venda.setCellValueFactory(new PropertyValueFactory<>("pr_venda"));
            tbvProdutos_saida.setItems(produtosSaida);

            empresas.setAll(new EmpresaDAO().getAll());
            cbxEmpresa.setItems(empresas);
            cbxEmpresa.setPromptText("Selecione...");

            btnCopiar_um.setDisable(true);
            btnCopiar_todos.setDisable(true);
            btnVoltar_um.setDisable(true);
            btnVoltar_todos.setDisable(true);
            txtPreco_venda.setDisable(true);
            txtQuantidade.setDisable(true);
            btnAdicionar_saida.setDisable(true);

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
    void btnAdicionar_saida_Click(ActionEvent event) {
        try {

            String rfid = tbvProdutos_encontrados.getSelectionModel().getSelectedItem().getCodigo();
            produtoSaida = new ProdutosSaidaMDL(rfid, new ProdutoDAO().get(rfid).getDescricao(), txtQuantidade.getText(), txtPreco_venda.getText());
            produtoSaida.valida(cbxEmpresa.getValue());
            produtosSaida.add(produtoSaida);

            limparCampos();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

    @FXML
    void btnConfirmar_Click(ActionEvent event) {
        try {
            saida = new Saida();
            saida.setData(new Date().fromDBServer());
            if (cbxEmpresa.getSelectionModel().getSelectedItem() != null)
                saida.setEmpresa(cbxEmpresa.getValue());

            for (int i = 0; i < produtosSaida.size(); i++) {
                saidaProdutos = new SaidaProdutos();
                saidaProdutos.setProduto(new ProdutoDAO().get(produtosSaida.get(i).getCodigo()));
                saidaProdutos.setPr_venda(new BigDecimal(produtosSaida.get(i).getPr_venda()));
                saidaProdutos.setQuantidade(new BigDecimal(produtosSaida.get(i).getQuantidade()));
                saida.addSaidaProdutos(saidaProdutos);
            }

            saida.commit();

            new Alert(Alert.AlertType.CONFIRMATION, "Registros processados com êxito!", ButtonType.OK).showAndWait();
            produtosSaida.clear();
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
                    produtoSaida = new ProdutosSaidaMDL(rfid, new ProdutoDAO().get(rfid).getDescricao(), "1", new ProdutoDAO().get(rfid).getPr_venda() + "");
                    produtosSaida.add(produtoSaida);
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
                txtPreco_venda.setDisable(false);
                txtQuantidade.setDisable(false);
                btnAdicionar_saida.setDisable(false);
                tbvProdutos_encontrados.setDisable(true);
                txtPreco_venda.setText(new ProdutoDAO().get(tbvProdutos_encontrados.getSelectionModel().getSelectedItem().getCodigo()).getPr_venda() + "");
                txtQuantidade.setText("1.00");
                txtPreco_venda.requestFocus();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnVoltar_todos_Click(ActionEvent event) {
        produtosSaida.clear();
    }

    @FXML
    void btnVoltar_um_Click(ActionEvent event) {
        if (tbvProdutos_saida.getSelectionModel().getSelectedItem() != null) {
            produtosSaida.remove(tbvProdutos_saida.getSelectionModel().getSelectedIndex());
        }
    }

    private void limparCampos() {
        tbvProdutos_encontrados.setDisable(false);
        txtPreco_venda.setDisable(true);
        txtQuantidade.setDisable(true);
        btnAdicionar_saida.setDisable(true);

        txtPreco_venda.clear();
        txtQuantidade.clear();
    }

}
