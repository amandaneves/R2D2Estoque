package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import util.jstage;

import java.io.IOException;

/**
 * Created by Amanda on 27/11/2016.
 */
public class AlertaCTR extends jstage {

    @FXML
    private ListView<String> ltvError;

    @FXML
    private Button btnOk;

    private ObservableList<String> mensagens;

    public AlertaCTR(ObservableList<String> mensagens) {
        try {
            this.OnCreate("/views/AlertaFRM.fxml");
            this.mensagens = mensagens;
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
        ltvError.setItems(mensagens);

        btnOk.setOnAction(btnevent -> {
            ((Stage) (btnOk.getScene().getWindow())).close();
        });
    }
}
