package data;

import application.R2D2Estoque;
import information.Entrada;
import information.EntradaProdutos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 04/12/2016.
 */
public class EntradaProdutosDAO {

    public void insert(Entrada entrada) throws SQLException {
        try {
            String queryEntrada = "INSERT INTO entradas (empresa_cnpj, data) VALUES (?, ?)";
            String queryEntradaProdutos = "INSERT INTO entradas_produtos (entrada_id, produto_id, pr_custo, quantidade) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(queryEntrada);
            cmd.setString(1, entrada.getEmpresa().getCnpj());
            cmd.setDate(2, entrada.getData().toSql());
            cmd.executeUpdate();

            PreparedStatement cmdEntrada = R2D2Estoque.Conexao.prepareStatement("SELECT MAX(entrada_id) as entrada_id FROM entradas");
            ResultSet result = cmdEntrada.executeQuery();
            if (result.next()) {
                entrada.setEntrada_id(result.getInt("entrada_id"));
            }

            PreparedStatement cmdEntradaProdutos = R2D2Estoque.Conexao.prepareStatement(queryEntradaProdutos);
            for (EntradaProdutos entradaProdutos : entrada.getEntradaProdutos()) {
                cmdEntradaProdutos.setInt(1, entrada.getEntrada_id());
                cmdEntradaProdutos.setInt(2, entradaProdutos.getProduto().getProduto_id());
                cmdEntradaProdutos.setBigDecimal(3, entradaProdutos.getPr_custo());
                cmdEntradaProdutos.setBigDecimal(4, entradaProdutos.getQuantidade());
                atualizaEstoque(entrada, entradaProdutos);
                cmdEntradaProdutos.addBatch();
            }
            cmdEntradaProdutos.executeBatch();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            R2D2Estoque.Conexao.rollback();
        }
    }

    private void atualizaEstoque(Entrada entrada, EntradaProdutos entradaProdutos)throws SQLException{
        new EstoqueDAO().commit(entrada, entradaProdutos);
    }

}
