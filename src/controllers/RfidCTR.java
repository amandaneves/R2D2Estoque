package controllers;

import application.R2D2Estoque;
import information.Rfid;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.ProdutosRfidMDL;
import util.RegistroIncompletoException;
import util.RfidImportacao;
import util.jstage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Amanda on 17/11/2016.
 */
public class RfidCTR extends jstage {

    @FXML
    private Label lblStatus_arquivo;

    @FXML
    private TextField txtArquivo_rfid;

    @FXML
    private Button btnBusca_arquivo;

    @FXML
    private TableView<ProdutosRfidMDL> tbvProdutos;

    @FXML
    private TableColumn<ProdutosRfidMDL, String> clnProduto_id;

    @FXML
    private TableColumn<?, ?> clnDescricao;

    @FXML
    private TableColumn<?, ?> clnCadastrado;

    @FXML
    private Button btnCadastrar_produtos;

    private Stage currentStage;
    private Rfid rfid;

    public RfidCTR() {
        try {
            this.OnCreate("/views/RfidFRM.fxml");
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

        currentStage = (Stage) btnBusca_arquivo.getScene().getWindow();

        //CONSTRUINDO TABLEVIEW.
        clnProduto_id.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        clnDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        clnCadastrado.setCellValueFactory(new PropertyValueFactory<>("cadastrado"));

        tbvProdutos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ProdutosRfidMDL>() {
            @Override
            public void changed(ObservableValue<? extends ProdutosRfidMDL> observable, ProdutosRfidMDL oldValue, ProdutosRfidMDL newValue) {
                if (newValue == null) {
                    btnCadastrar_produtos.setDisable(true);
                } else {
                    btnCadastrar_produtos.setDisable(newValue.getCadastrado().equals("Sim"));
                }
            }
        });

        rfid = new Rfid();
        try {
            if (rfid.get() != null) {
                txtArquivo_rfid.setText(rfid.get().getPath());

                if (!rfid.get().exists()) {
                    lblStatus_arquivo.setText("Arquivo não encontrado!");
                    lblStatus_arquivo.setTextFill(Color.RED);
                } else {
                    lblStatus_arquivo.setText("Arquivo encontrado.");
                    lblStatus_arquivo.setTextFill(Color.GREEN);

                    processaArquivo(new File(txtArquivo_rfid.getText()));
                }
            } else {
                lblStatus_arquivo.setText("Selecione um arquivo RFID.");
                lblStatus_arquivo.setTextFill(Color.BLUE);
            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
            R2D2Estoque.mostraAlerta(e1.getMessage());
        }
    }

    @FXML
    void btnCadastrar_produtos_Click(ActionEvent event) {
        ProdutosRfidMDL currentProd = tbvProdutos.getSelectionModel().getSelectedItem();

        if (currentProd == null) {
            btnCadastrar_produtos.setDisable(true);
            return;
        }

        if (currentProd.getCadastrado().equals("Sim")) {
            btnCadastrar_produtos.setDisable(true);
            return;
        }

        ProdutoCTR ctr = new ProdutoCTR(ProdutoCTR.MODO.RFID, currentProd);
        ctr.setTitle("Cadastro de Produtos por Rfid");
        ctr.setResizable(false);
        ctr.initModality(Modality.APPLICATION_MODAL);
        ctr.showAndWait();

        tbvProdutos.getSelectionModel().selectNext();
        tbvProdutos.requestFocus();
    }

    @FXML
    void btnBusca_arquivo_Click(ActionEvent event) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Selecione o arquivo RFID");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("RFID Files", "*.rfid"));
            File arquivoSelecionado = fileChooser.showOpenDialog(currentStage);
            processaArquivo(arquivoSelecionado);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        } catch (Exception e) {
            e.printStackTrace();
            R2D2Estoque.mostraAlerta(e.getMessage());
        }
    }

    private void processaArquivo(File file) throws Exception {
        if (file != null) {
            txtArquivo_rfid.setText(file.getPath());
            rfid.setPath(file.getPath());
            rfid.commit();

            //COMEÇANDO A IMPORTAÇÃO DO ARQUIVO.
            RfidImportacao importacao = new RfidImportacao();

            if (importacao.importar(rfid) == 0) {
                lblStatus_arquivo.setText("Nenhum produto importado!");
                lblStatus_arquivo.setTextFill(Color.RED);
                return;
            }

            //PREENCHENDO O TABLEVIEW.
            tbvProdutos.setItems(importacao.getProdutos());
        }
    }

}
