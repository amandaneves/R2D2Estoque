package business;

import data.SaidaProdutosDAO;
import information.Saida;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by AMANDA on 05/12/2016.
 */
public class SaidaProdutosBLL {

    public void commit(Saida saida) throws SQLException, RegistroIncompletoException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        if (saida.getEmpresa().getCnpj() == null) {
            mensagens.add("Informe a EMPRESA para a Saída.");
        }

        if (saida.getSaidaProdutos().size() == 0) {
            mensagens.add("Informe pelo menos UM PRODUTO para a Saída.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }

        new SaidaProdutosDAO().insert(saida);
    }

}
