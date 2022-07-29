package business;

import data.EntradaProdutosDAO;
import information.Entrada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 05/12/2016.
 */
public class EntradaProdutosBLL {

    public void commit(Entrada entrada) throws SQLException, RegistroIncompletoException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        if (entrada.getEmpresa().getCnpj() == null) {
            mensagens.add("Informe a EMPRESA para a Entrada.");
        }

        if (entrada.getEntradaProdutos().size() == 0) {
            mensagens.add("Informe pelo menos UM PRODUTO para a Entrada.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }

        new EntradaProdutosDAO().insert(entrada);
    }

}
