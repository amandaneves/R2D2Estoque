package util;

import application.R2D2Estoque;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Amanda on 27/11/2016.
 */
public class RegistroIncompletoException extends Exception {

    private ObservableList<String> mensagens = FXCollections.observableArrayList();

    public RegistroIncompletoException(ObservableList<String> mensagens) {
        this.mensagens = mensagens;
    }

    public RegistroIncompletoException(String mensagem) {
        this.mensagens.add(mensagem);
    }

    public void mostraAlerta() {
        R2D2Estoque.mostraAlerta(mensagens);
    }

    @Override
    public void printStackTrace() {
        mensagens.forEach(System.out::println);
    }

}
