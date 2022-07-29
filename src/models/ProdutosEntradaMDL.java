package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.math.BigDecimal;

/**
 * Created by Amanda on 04/12/2016.
 */
public class ProdutosEntradaMDL {

    private SimpleStringProperty codigo;
    private SimpleStringProperty descricao;
    private SimpleStringProperty quantidade;
    private SimpleStringProperty pr_custo;

    public ProdutosEntradaMDL(String codigo, String descricao, String quantidade, String pr_custo) {
        this.codigo = new SimpleStringProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
        this.quantidade = new SimpleStringProperty(quantidade);
        this.pr_custo = new SimpleStringProperty(pr_custo);
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

    public String getPr_custo() {
        return pr_custo.get();
    }

    public SimpleStringProperty pr_custoProperty() {
        return pr_custo;
    }

    public void setPr_custo(String pr_custo) {
        this.pr_custo.set(pr_custo);
    }

    public void valida() throws RegistroIncompletoException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        if (this.getPr_custo().trim().isEmpty()) {
            mensagens.add("Informe o PREÇO DE CUSTO do produto para Entrada.");
        }

        if (this.getQuantidade().trim().isEmpty()) {
            mensagens.add("Informe a QUANTIDADE do produto para Entrada.");
        } else if (new BigDecimal(this.getQuantidade()).equals(new BigDecimal("0"))) {
            mensagens.add("A QUANTIDADE deve ser maior que zero.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }
    }

}
