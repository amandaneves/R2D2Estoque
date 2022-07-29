package data;

import application.R2D2Estoque;
import information.*;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 04/12/2016.
 */
public class EstoqueDAO {

    private Estoque estoque;

    public void commit(Entrada entrada, EntradaProdutos entradaProdutos) throws SQLException {
        estoque = new Estoque();
        estoque.setEmpresa(entrada.getEmpresa());
        estoque.setProduto(entradaProdutos.getProduto());
        if (existeEsteProdutoNoEstoque(entradaProdutos.getProduto(), entrada.getEmpresa())) {
            estoque.setQuantidade(pegaEstoqueAtual(entradaProdutos.getProduto().getProduto_id(), entrada.getEmpresa().getCnpj()).add(entradaProdutos.getQuantidade()));
            update(estoque);
        } else {
            estoque.setQuantidade(entradaProdutos.getQuantidade());
            insert(estoque);
        }
    }

    public void commit(Saida saida, SaidaProdutos saidaProdutos) throws SQLException {
        estoque = new Estoque();
        estoque.setEmpresa(saida.getEmpresa());
        estoque.setProduto(saidaProdutos.getProduto());
        if (existeEsteProdutoNoEstoque(saidaProdutos.getProduto(), saida.getEmpresa())) {
            estoque.setQuantidade(pegaEstoqueAtual(saidaProdutos.getProduto().getProduto_id(), saida.getEmpresa().getCnpj()).subtract(saidaProdutos.getQuantidade()));
            update(estoque);
        } else {
            estoque.setQuantidade(saidaProdutos.getQuantidade());
            insert(estoque);
        }
    }

    private void insert(Estoque estoque) throws SQLException {
        String query = "INSERT INTO estoque (produto_id, empresa_cnpj, quantidade) VALUES (?, ?, ?)";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setInt(1, estoque.getProduto().getProduto_id());
            cmd.setString(2, estoque.getEmpresa().getCnpj());
            cmd.setBigDecimal(3, estoque.getQuantidade());
            cmd.executeUpdate();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }
    }

    private void update(Estoque estoque) throws SQLException {
        String query = "UPDATE estoque SET quantidade = ? WHERE produto_id = ? AND empresa_cnpj = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setBigDecimal(1, estoque.getQuantidade());
            cmd.setInt(2, estoque.getProduto().getProduto_id());
            cmd.setString(3, estoque.getEmpresa().getCnpj());
            cmd.executeUpdate();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }
    }

    public boolean existeEsteProdutoNoEstoque(Produto produto, Empresa empresa) throws SQLException {
        boolean retorno = false;
        String query = "SELECT * FROM estoque WHERE produto_id = ? AND empresa_cnpj = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setInt(1, produto.getProduto_id());
            cmd.setString(2, empresa.getCnpj());
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public BigDecimal pegaEstoqueAtual(int produto_id, String empresa_cnpj) throws SQLException {
        BigDecimal retorno = new BigDecimal("0");
        String query = "SELECT quantidade FROM estoque WHERE produto_id = ? AND empresa_cnpj = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setInt(1, produto_id);
            cmd.setString(2, empresa_cnpj);
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    retorno = result.getBigDecimal("quantidade");
                }
            }
        }
        return retorno;
    }
}
