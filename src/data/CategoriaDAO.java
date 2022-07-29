package data;

import application.R2D2Estoque;
import information.Categoria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 27/11/2016.
 */
public class CategoriaDAO {

    public void insert(Categoria categoria)throws SQLException{
        String query = "INSERT INTO categorias (descricao) values (?)";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, categoria.getDescricao());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public void update(Categoria categoria)throws SQLException{
        String query = "UPDATE categorias SET descricao = ? WHERE categoria_id = ?";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, categoria.getDescricao());
            cmd.setInt(2, categoria.getCategoria_id());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public ObservableList<Categoria> getAll()throws SQLException{
        String query = "SELECT * FROM categorias";
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        Categoria categoria = null;
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            try(ResultSet result = cmd.executeQuery()){
                while(result.next()){
                    categoria = new Categoria();
                    categoria.setCategoria_id(result.getInt("categoria_id"));
                    categoria.setDescricao(result.getString("descricao"));
                    categorias.add(categoria);
                }
            }
        }
        return categorias;
    }

    public Categoria get(int categoria_id)throws SQLException{
        String query = "SELECT * FROM categorias WHERE categoria_id = ?";
        Categoria categoria = new Categoria();
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setInt(1, categoria_id);
            try(ResultSet result = cmd.executeQuery()){
                if(result.next()){
                    categoria.setCategoria_id(result.getInt("categoria_id"));
                    categoria.setDescricao(result.getString("descricao"));
                }
            }
        }
        return categoria;
    }

    public void delete(Categoria categoria)throws SQLException{
        String query = "DELETE FROM categorias WHERE categoria_id = ?";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setInt(1, categoria.getCategoria_id());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

}
