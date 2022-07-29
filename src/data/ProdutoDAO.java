package data;

import application.R2D2Estoque;
import information.Categoria;
import information.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Date;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 28/11/2016.
 */
public class ProdutoDAO {

    public void insert(Produto produto)throws SQLException {
        String query = "INSERT INTO produtos (rfid, descricao, categoria_id, dt_inventario, pr_custo, pr_medio, " +
                "pr_venda) values (?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, produto.getRfid());
            cmd.setString(2, produto.getDescricao());
            cmd.setInt(3, produto.getCategoria().getCategoria_id());
            cmd.setDate(4, produto.getDt_inventario().toSql());
            cmd.setBigDecimal(5, produto.getPr_custo());
            cmd.setBigDecimal(6, produto.getPr_medio());
            cmd.setBigDecimal(7, produto.getPr_venda());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public void update(Produto produto)throws SQLException{
        String query = "UPDATE produtos SET descricao = ?, categoria_id = ?, pr_custo = ?, pr_venda = ? WHERE produto_id = ?";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, produto.getDescricao());
            cmd.setInt(2, produto.getCategoria().getCategoria_id());
            cmd.setBigDecimal(3, produto.getPr_custo());
            cmd.setBigDecimal(4, produto.getPr_venda());
            cmd.setInt(5, produto.getProduto_id());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public ObservableList<Produto> getAll()throws SQLException{
        String query = "SELECT * FROM produtos";
        ObservableList<Produto> produtos = FXCollections.observableArrayList();
        Produto produto = null;
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            try(ResultSet result = cmd.executeQuery()){
                while(result.next()){
                    produto = new Produto();
                    produto.setProduto_id(result.getInt("produto_id"));
                    produto.setRfid(result.getString("rfid"));
                    produto.setDescricao(result.getString("descricao"));
                    produto.setCategoria(new CategoriaDAO().get(result.getInt("categoria_id")));
                    produto.setDt_inventario(new Date(result.getDate("dt_inventario")));
                    produto.setPr_custo(result.getBigDecimal("pr_custo"));
                    produto.setPr_medio(result.getBigDecimal("pr_medio"));
                    produto.setPr_venda(result.getBigDecimal("pr_venda"));
                    produtos.add(produto);
                }
            }
        }
        return produtos;
    }

    public Produto get(int produto_id)throws SQLException{
        String query = "SELECT * FROM produtos WHERE produto_id = ?";
        Produto produto = new Produto();
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setInt(1, produto_id);
            try(ResultSet result = cmd.executeQuery()){
                if(result.next()){
                    produto.setProduto_id(result.getInt("produto_id"));
                    produto.setRfid(result.getString("rfid"));
                    produto.setDescricao(result.getString("descricao"));
                    produto.setCategoria(new Categoria().get(result.getInt("categoria_id")));
                    produto.setDt_inventario(new Date(result.getDate("dt_inventario")));
                    produto.setPr_custo(result.getBigDecimal("pr_custo"));
                    produto.setPr_medio(result.getBigDecimal("pr_medio"));
                    produto.setPr_venda(result.getBigDecimal("pr_venda"));
                }
            }
        }
        return produto;
    }

    public Produto get(String rfid)throws SQLException{
        String query = "SELECT * FROM produtos WHERE rfid = ?";
        Produto produto = new Produto();
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, rfid);
            try(ResultSet result = cmd.executeQuery()){
                if(result.next()){
                    produto.setProduto_id(result.getInt("produto_id"));
                    produto.setRfid(result.getString("rfid"));
                    produto.setDescricao(result.getString("descricao"));
                    produto.setCategoria(new Categoria().get(result.getInt("categoria_id")));
                    produto.setDt_inventario(new Date(result.getDate("dt_inventario")));
                    produto.setPr_custo(result.getBigDecimal("pr_custo"));
                    produto.setPr_medio(result.getBigDecimal("pr_medio"));
                    produto.setPr_venda(result.getBigDecimal("pr_venda"));
                }
            }
        }
        return produto;
    }


    public void delete(Produto produto)throws SQLException{
        String query = "DELETE FROM produtos WHERE produto_id = ?";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setInt(1, produto.getProduto_id());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public boolean temEsseRfidCadastrado(String rfid)throws SQLException{
        boolean retorno = false;
        String query = "SELECT rfid FROM produtos WHERE rfid = ?";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, rfid);
            try(ResultSet result = cmd.executeQuery()){
                if(result.next()){
                    retorno = true;
                }
            }
        }
        return retorno;
    }
}
