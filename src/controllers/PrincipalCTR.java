package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.stage.Modality;
import util.jstage;

import java.io.IOException;

public class PrincipalCTR extends jstage {

    @FXML
    private Menu mnuCadastros;

    @FXML
    private MenuItem mniUsuarios;

    @FXML
    private MenuItem mniProdutos;

    @FXML
    private MenuItem mniCategorias;

    @FXML
    private MenuItem mniEmpresas;

    @FXML
    private Menu mnuMovimentacoes;

    @FXML
    private MenuItem mniRfid;

    @FXML
    private MenuItem mniEntrada_produtos;

    @FXML
    private MenuItem mniSaida_produtos;

    @FXML
    private Menu mnuConsultas;

    @FXML
    private MenuItem mniTabela_produtos;

    @FXML
    private MenuItem mniTabela_produtos_rfid;

    @FXML
    private Button btnUsuarios;

    @FXML
    private Button btnProdutos;

    @FXML
    private Separator sptBar;

    @FXML
    private Button btnRfid;

    @FXML
    private Button btnEntrada_produtos;

    @FXML
    private Button btnSaida_produtos;

    public PrincipalCTR() {
        try {
            this.OnCreate("/views/PrincipalFRM.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void OnCreate(String fxml) throws IOException {
        super.OnCreate(fxml);
    }

    @FXML
    void mniUsuarios_Click(ActionEvent event) {
        UsuarioCTR ctr = new UsuarioCTR();
        ctr.setTitle("Cadastro de Usuários");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniProdutos_Click(ActionEvent event) {
        ProdutoCTR ctr = new ProdutoCTR();
        ctr.setTitle("Cadastro de Produtos");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniCategorias_Click(ActionEvent event) {
        CategoriaCTR ctr = new CategoriaCTR();
        ctr.setTitle("Cadastro de Categorias de Produtos");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniEmpresas_Click(ActionEvent event) {
        EmpresaCTR ctr = new EmpresaCTR();
        ctr.setTitle("Cadastro de Empresas");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniRfid_Click(ActionEvent event) {
        RfidCTR ctr = new RfidCTR();
        ctr.setTitle("RFID");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniEntrada_produtos_Click(ActionEvent event) {
        EntradaProdutosCTR ctr = new EntradaProdutosCTR();
        ctr.setTitle("Entrada de Produtos");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniSaida_produtos_Click(ActionEvent event) {
        SaidaProdutosCTR ctr = new SaidaProdutosCTR();
        ctr.setTitle("Saída de Produtos");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniTabela_produtos_Click(ActionEvent event) {
        TabelaProdutosCTR ctr = new TabelaProdutosCTR();
        ctr.setTitle("Tabela de Produtos");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

    @FXML
    void mniTabela_produtos_rfid_Click(ActionEvent event) {
        TabelaProdutosRfidCTR ctr = new TabelaProdutosRfidCTR();
        ctr.setTitle("Tabela de Produtos com o RFID");
        ctr.setResizable(false);
        ctr.initModality(Modality.WINDOW_MODAL);
        ctr.show();
    }

}
