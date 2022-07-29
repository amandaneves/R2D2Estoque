package controllers;

import application.R2D2Estoque;
import enums.Funcao;
import enums.Genero;
import information.Usuario;
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
 * Created by Amanda on 14/11/2016.
 */
public class UsuarioCTR extends jstage {

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
    private TabPane tbpUsuario;

    @FXML
    private Tab tabPerfil;

    @FXML
    private AnchorPane acpPerfil;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtTelefone;

    @FXML
    private RadioButton rbtMasculino;

    @FXML
    private RadioButton rbtFeminino;

    @FXML
    private Tab tabConta;

    @FXML
    private AnchorPane acpConta;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtSenha;

    @FXML
    private TextField txtConfirma_senha;

    @FXML
    private Button btnLibe_bloq_senha;

    @FXML
    private TextField txtEmail;

    @FXML
    private Tab tabAcesso;

    @FXML
    private AnchorPane acpAcesso;

    @FXML
    private CheckBox ckxUsuarios;

    @FXML
    private CheckBox ckxProdutos;

    @FXML
    private CheckBox ckxCategorias;

    @FXML
    private CheckBox ckxEmpresas;

    @FXML
    private CheckBox ckxRfid;

    @FXML
    private CheckBox ckxEntrada_produtos;

    @FXML
    private CheckBox ckxSaida_produtos;

    private Usuario usuario;

    public UsuarioCTR() {
        try {
            this.OnCreate("/views/UsuarioFRM.fxml");
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

        Mascaras.setMaxLength(txtNome, 60);
        Mascaras.setMaxLength(txtTelefone, 11);
        Mascaras.setMaxLength(txtEmail, 60);
        Mascaras.setMaxLength(txtUsuario, 20);
        Mascaras.setMaxLength(txtSenha, 12);

        ToggleGroup generoGroup = new ToggleGroup();
        rbtFeminino.setToggleGroup(generoGroup);
        rbtMasculino.setToggleGroup(generoGroup);

        btnExcluir.setDisable(true);
        btnSalvar.setDisable(true);
        btnLimpar.setDisable(true);
        acpDados.setDisable(true);

        btnExcluir.setFocusTraversable(true);
        btnLimpar.setFocusTraversable(true);
    }

    @FXML
    void btnNovo_Click(ActionEvent event) {

        usuario = new Usuario();

        btnSalvar.setDisable(false);
        btnLimpar.setDisable(false);
        acpDados.setDisable(false);

        btnNovo.setDisable(true);
        btnEditar.setDisable(true);
        btnLibe_bloq_senha.setDisable(true);

        txtNome.requestFocus();
    }

    @FXML
    void btnEditar_Click(ActionEvent event) {
        try {
            usuario = new Usuario();
            usuario = (Usuario) R2D2Estoque.mostraPesquisa(usuario.getAll());

            int tmpId = usuario.getUsuario_id();
            usuario = usuario.get(tmpId);
            usuario.setModo(Funcao.ALTERACAO);

            txtNome.setText(usuario.getNome());
            txtTelefone.setText(usuario.getTelefone());
            if (usuario.getSexo() == Genero.FEMININO) {
                rbtFeminino.setSelected(true);
            } else {
                rbtMasculino.setSelected(true);
            }

            txtUsuario.setText(usuario.getUsuarioConta().getUsername());
            txtEmail.setText(usuario.getEmail());
            txtSenha.setText(usuario.getUsuarioConta().getSenha());
            txtConfirma_senha.setText(usuario.getUsuarioConta().getSenha());

            ckxUsuarios.setSelected(usuario.getUsuarioAcesso().isUsuarios());
            ckxProdutos.setSelected(usuario.getUsuarioAcesso().isProdutos());
            ckxCategorias.setSelected(usuario.getUsuarioAcesso().isCategorias());
            ckxEmpresas.setSelected(usuario.getUsuarioAcesso().isEmpresas());
            ckxRfid.setSelected(usuario.getUsuarioAcesso().isRfid());
            ckxEntrada_produtos.setSelected(usuario.getUsuarioAcesso().isEntrada());
            ckxSaida_produtos.setSelected(usuario.getUsuarioAcesso().isSaida());

            btnSalvar.setDisable(false);
            btnExcluir.setDisable(false);
            btnLimpar.setDisable(false);
            acpDados.setDisable(false);

            btnNovo.setDisable(true);
            btnEditar.setDisable(true);
            txtSenha.setDisable(true);
            txtConfirma_senha.setDisable(true);

            txtNome.requestFocus();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (PesquisaException e) {

        }
    }

    @FXML
    void btnExcluir_Click(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setContentText("Tem certeza que deseja excluir este Usuário?");

            ButtonType buttonTypeSim = new ButtonType("Sim");
            ButtonType buttonTypeNao = new ButtonType("Não");

            alert.getButtonTypes().setAll(buttonTypeSim, buttonTypeNao);
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == buttonTypeSim) {
                usuario.delete();
                btnLimpar.fire();
                new Alert(Alert.AlertType.CONFIRMATION, "Usuário excluído com êxito!", ButtonType.OK).showAndWait();
            } else {
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

    @FXML
    void btnLimpar_Click(ActionEvent event) {
        txtNome.clear();
        txtTelefone.clear();
        txtEmail.clear();
        txtUsuario.clear();
        txtSenha.clear();
        txtConfirma_senha.clear();

        rbtFeminino.setSelected(false);
        rbtMasculino.setSelected(false);
        ckxUsuarios.setSelected(false);
        ckxProdutos.setSelected(false);
        ckxCategorias.setSelected(false);
        ckxEmpresas.setSelected(false);
        ckxRfid.setSelected(false);
        ckxEntrada_produtos.setSelected(false);
        ckxSaida_produtos.setSelected(false);

        tbpUsuario.getSelectionModel().select(0);

        acpDados.setDisable(true);
        btnLimpar.setDisable(true);
        btnSalvar.setDisable(true);
        btnExcluir.setDisable(true);

        btnNovo.setDisable(false);
        btnEditar.setDisable(false);
        txtSenha.setDisable(false);
        txtConfirma_senha.setDisable(false);
        btnLibe_bloq_senha.setDisable(false);
    }

    @FXML
    void btnSalvar_Click(ActionEvent event) {
        try {
            usuario.setNome(txtNome.getText());
            usuario.setTelefone(txtTelefone.getText());
            usuario.setSexo(rbtFeminino.isSelected() ? Genero.FEMININO : Genero.MASCULINO);
            usuario.setEmail(txtEmail.getText());
            usuario.getUsuarioConta().setUsername(txtUsuario.getText());
            usuario.getUsuarioConta().setSenha(txtSenha.getText());
            usuario.getUsuarioConta().setConfirmaSenha(txtConfirma_senha.getText());
            usuario.getUsuarioAcesso().setUsuarios(ckxUsuarios.isSelected());
            usuario.getUsuarioAcesso().setProdutos(ckxProdutos.isSelected());
            usuario.getUsuarioAcesso().setCategorias(ckxCategorias.isSelected());
            usuario.getUsuarioAcesso().setEmpresas(ckxEmpresas.isSelected());
            usuario.getUsuarioAcesso().setRfid(ckxRfid.isSelected());
            usuario.getUsuarioAcesso().setEntrada(ckxEntrada_produtos.isSelected());
            usuario.getUsuarioAcesso().setSaida(ckxSaida_produtos.isSelected());
            usuario.commit();

            new Alert(Alert.AlertType.CONFIRMATION, "Registro processado com êxito!", ButtonType.OK).showAndWait();
            btnLimpar.fire();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (RegistroIncompletoException e) {
            e.mostraAlerta();
        }
    }

    @FXML
    void btnLibe_bloq_senha_Click(ActionEvent event) {
        if (txtSenha.isDisable()) {
            txtSenha.setDisable(false);
            txtConfirma_senha.setDisable(false);
        } else {
            txtSenha.setDisable(true);
            txtConfirma_senha.setDisable(true);
        }
    }

}
