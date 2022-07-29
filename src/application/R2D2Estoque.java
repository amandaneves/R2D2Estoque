package application;

import controllers.AlertaCTR;
import controllers.LoginCTR;
import controllers.PesquisaCTR;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import util.Conexao;
import util.PesquisaException;

import java.sql.Connection;

public class R2D2Estoque extends Application {

    public static Connection Conexao;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Conexao = new Conexao().getConexao();

        if (R2D2Estoque.Conexao == null || R2D2Estoque.Conexao.isClosed()) {
            new Alert(Alert.AlertType.WARNING, "Não foi possível conectar ao banco de dados", ButtonType.OK).showAndWait();
            System.exit(0);
        }

        LoginCTR frm = new LoginCTR();
        frm.setTitle("R2D2 Estoque - Entrar");
        frm.initModality(Modality.APPLICATION_MODAL);
        frm.setResizable(false);
        frm.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static boolean mostraAlerta(ObservableList<String> mensagens) {
        boolean ok = mensagens.size() <= 0;
        if (!ok) {
            AlertaCTR ctr = new AlertaCTR(mensagens);
            ctr.setTitle("Validação");
            ctr.setResizable(false);
            ctr.initModality(Modality.APPLICATION_MODAL);
            ctr.show();
        }
        return ok;
    }

    public static boolean mostraAlerta(String mensagem) {
        if (!mensagem.isEmpty()) {
            AlertaCTR ctr = new AlertaCTR(FXCollections.observableArrayList(mensagem));
            ctr.setTitle("Validação");
            ctr.setResizable(false);
            ctr.initModality(Modality.APPLICATION_MODAL);
            ctr.show();
            return false;
        }
        return true;
    }

    public static Object mostraPesquisa(ObservableList lista) throws PesquisaException {
        PesquisaCTR ctr = new PesquisaCTR(lista);
        ctr.setTitle("Pesquisa");
        ctr.setResizable(false);
        ctr.initModality(Modality.APPLICATION_MODAL);
        ctr.showAndWait();
        if (ctr.getSelectedRow() == null)
            throw new PesquisaException();
        return ctr.getSelectedRow();
    }
}
