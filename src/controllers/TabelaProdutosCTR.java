package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import util.jstage;

import java.io.IOException;

/**
 * Created by Amanda on 23/11/2016.
 */
public class TabelaProdutosCTR extends jstage {

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtDescricao;

    @FXML
    private ComboBox<?> cbxFilial;

    @FXML
    private Button btnFiltrar;

    @FXML
    private TableView<?> tbvProdutos;

    @FXML
    private TableColumn<?, ?> clnCodigo;

    @FXML
    private TableColumn<?, ?> clnDescricao;

    @FXML
    private TableColumn<?, ?> clnCategoria;

    @FXML
    private TableColumn<?, ?> clnPreco_custo;

    @FXML
    private TableColumn<?, ?> clnPreco_medio;

    @FXML
    private TableColumn<?, ?> clnPreco_venda;

    @FXML
    private TableColumn<?, ?> clnEstoque;

    @FXML
    private TableColumn<?, ?> clnValor_estoque;

    public TabelaProdutosCTR(){
        try {
            this.OnCreate("/views/TabelaProdutosFRM.fxml");
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
    }

    @FXML
    void btnFiltrar_Click(ActionEvent event) {

    }

}
