package models;

import data.EstoqueDAO;
import data.ProdutoDAO;
import information.Empresa;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by Amanda on 04/12/2016.
 */
public class ProdutosSaidaMDL {

    private SimpleStringProperty codigo;
    private SimpleStringProperty descricao;
    private SimpleStringProperty quantidade;
    private SimpleStringProperty pr_venda;

    public ProdutosSaidaMDL(String codigo, String descricao, String quantidade, String pr_venda) {
        this.codigo = new SimpleStringProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
        this.quantidade = new SimpleStringProperty(quantidade);
        this.pr_venda = new SimpleStringProperty(pr_venda);
    }

    public String getCodigo() {
        return codigo.get();
    }

    public SimpleStringProperty codigoProperty() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo.set(codigo);
    }

    public String getDescricao() {
        return descricao.get();
    }

    public SimpleStringProperty descricaoProperty() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao.set(descricao);
    }

    public String getQuantidade() {
        return quantidade.get();
    }

    public SimpleStringProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade.set(quantidade);
    }

    public String getPr_venda() {
        return pr_venda.get();
    }

    public SimpleStringProperty pr_vendaProperty() {
        return pr_venda;
    }

    public void setPr_venda(String pr_venda) {
        this.pr_venda.set(pr_venda);
    }

    public void valida(Empresa empresa) throws RegistroIncompletoException, SQLException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        BigDecimal estoqueAtual;

        if (empresa == null) {
            mensagens.add("Informe a EMPRESA para verificar o Estoque.");
        } else {
            estoqueAtual = new EstoqueDAO().pegaEstoqueAtual(new ProdutoDAO().get(this.getCodigo()).getProduto_id(), empresa.getCnpj());
            if (estoqueAtual.compareTo(new BigDecimal(this.getQuantidade())) < 0) {
                mensagens.add("ESTOQUE insuficiente (" + estoqueAtual + ").");
            }
        }

        if (this.getPr_venda().trim().isEmpty()) {
            mensagens.add("Informe o PREÇO DE VENDA do produto para Saída.");
        }

        if (this.getQuantidade().trim().isEmpty()) {
            mensagens.add("Informe a QUANTIDADE do produto para Saída.");
        } else if (new BigDecimal(this.getQuantidade()).equals(new BigDecimal("0"))) {
            mensagens.add("A QUANTIDADE deve ser maior que zero.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }
    }
}
