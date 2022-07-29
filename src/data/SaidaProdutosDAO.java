package data;

import application.R2D2Estoque;
import information.Saida;
import information.SaidaProdutos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 05/12/2016.
 */
public class SaidaProdutosDAO {

    public void insert(Saida saida) throws SQLException {
        try {
            String querySaida = "INSERT INTO saidas (empresa_cnpj, data) VALUES (?, ?)";
            String querySaidaProdutos = "INSERT INTO saidas_produtos (saida_id, produto_id, pr_venda, quantidade) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(querySaida);
            cmd.setString(1, saida.getEmpresa().getCnpj());
            cmd.setDate(2, saida.getData().toSql());
            cmd.executeUpdate();

            PreparedStatement cmdSaida = R2D2Estoque.Conexao.prepareStatement("SELECT MAX(saida_id) as saida_id FROM saidas");
            ResultSet result = cmdSaida.executeQuery();
            if (result.next()) {
                saida.setSaida_id(result.getInt("saida_id"));
            }

            PreparedStatement cmdSaidaProdutos = R2D2Estoque.Conexao.prepareStatement(querySaidaProdutos);
            for (SaidaProdutos saidaProdutos : saida.getSaidaProdutos()) {
                cmdSaidaProdutos.setInt(1, saida.getSaida_id());
                cmdSaidaProdutos.setInt(2, saidaProdutos.getProduto().getProduto_id());
                cmdSaidaProdutos.setBigDecimal(3, saidaProdutos.getPr_venda());
                cmdSaidaProdutos.setBigDecimal(4, saidaProdutos.getQuantidade());
                atualizaEstoque(saida, saidaProdutos);
                cmdSaidaProdutos.addBatch();
            }
            cmdSaidaProdutos.executeBatch();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            R2D2Estoque.Conexao.rollback();
        }
    }

    private void atualizaEstoque(Saida saida, SaidaProdutos saidaProdutos)throws SQLException{
        new EstoqueDAO().commit(saida, saidaProdutos);
    }

}
