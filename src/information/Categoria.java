package information;

import business.CategoriaBLL;
import enums.Funcao;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 27/11/2016.
 */
public class Categoria implements Information{

    private int categoria_id;
    private String descricao;
    private Funcao modo = Funcao.INCLUSAO;

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Funcao getModo() {
        return modo;
    }

    public void setModo(Funcao modo) {
        this.modo = modo;
    }

    @Override
    public void commit() throws SQLException, RegistroIncompletoException {
        new CategoriaBLL().commit(this);
    }

    @Override
    public void delete() throws SQLException, RegistroIncompletoException {
        new CategoriaBLL().delete(this);
    }

    public ObservableList<Categoria> getAll()throws SQLException{
        return new CategoriaBLL().getAll();
    }

    public Categoria get(int categoria_id)throws SQLException{
        return new CategoriaBLL().get(categoria_id);
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
