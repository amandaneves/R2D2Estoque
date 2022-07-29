package business;

import data.ProdutoDAO;
import information.Produto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.math.BigDecimal;
import java.sql.SQLException;

import static enums.Funcao.INCLUSAO;

/**
 * Created by Amanda on 28/11/2016.
 */
public class ProdutoBLL {

    public void commit(Produto produto) throws SQLException, RegistroIncompletoException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        if (produto.getRfid().trim().isEmpty()) {
            mensagens.add("Informe o código RFID do produto.");
        }

        if (produto.getDescricao().trim().isEmpty()) {
            mensagens.add("Informe a DESCRIÇÃO do produto.");
        }

        if (produto.getCategoria() == null) {
            mensagens.add("Informe a CATEGORIA do produto.");
        }

        if (produto.getDt_inventario().toString().isEmpty()) {
            mensagens.add("Informe a DATA DE INVENTÁRIO do produto.");
        }

        if (produto.getPr_custo().equals(new BigDecimal("0"))) {
            mensagens.add("Informe o PREÇO DE CUSTO do produto.");
        }

        if (produto.getPr_medio().equals(new BigDecimal("0"))) {
            mensagens.add("Informe o PREÇO MÉDIO do produto.");
        }

        if (produto.getPr_venda().equals(new BigDecimal("0"))) {
            mensagens.add("Informe o PREÇO DE VENDA do produto.");
        }

        if (produto.getModo() == INCLUSAO && new ProdutoDAO().temEsseRfidCadastrado(produto.getRfid())) {
            mensagens.add("Já existe um produto com este código RFID cadastrado no sistema.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }

        if (produto.getModo() == INCLUSAO) {
            new ProdutoDAO().insert(produto);
        } else {
            new ProdutoDAO().update(produto);
        }
    }

    public ObservableList<Produto> getAll() throws SQLException {
        return new ProdutoDAO().getAll();
    }

    public Produto get(int produto_id) throws SQLException {
        return new ProdutoDAO().get(produto_id);
    }

    public Produto getByRfid(String rfid) throws SQLException {
        return new ProdutoDAO().get(rfid);
    }

    public void delete(Produto produto) throws SQLException, RegistroIncompletoException {
        try {
            new ProdutoDAO().delete(produto);
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23503") == 0) {
                throw new RegistroIncompletoException("Registro em uso, exclusão não permitida!");
            }
            throw e;
        }
    }

}
