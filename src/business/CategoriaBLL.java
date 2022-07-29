package business;

import data.CategoriaDAO;
import information.Categoria;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

import static enums.Funcao.INCLUSAO;

/**
 * Created by Amanda on 27/11/2016.
 */
public class CategoriaBLL {

    public void commit(Categoria categoria) throws SQLException, RegistroIncompletoException {

        if (categoria.getDescricao().trim().isEmpty()) {
            throw new RegistroIncompletoException("Informe a DESCRIÇÃO da categoria.");
        }

        if (categoria.getModo() == INCLUSAO) {
            new CategoriaDAO().insert(categoria);
        } else {
            new CategoriaDAO().update(categoria);
        }

    }

    public ObservableList<Categoria> getAll() throws SQLException {
        return new CategoriaDAO().getAll();
    }

    public Categoria get(int categoria_id) throws SQLException {
        return new CategoriaDAO().get(categoria_id);
    }

    public void delete(Categoria categoria) throws SQLException, RegistroIncompletoException {
        try {
            new CategoriaDAO().delete(categoria);
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23503") == 0) {
                throw new RegistroIncompletoException("Registro em uso, exclusão não permitida!");
            }
            throw e;
        }
    }
}
