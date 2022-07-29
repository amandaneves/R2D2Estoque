package models;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Amanda on 04/12/2016.
 */
public class ProdutosRfidMDL {

    private SimpleStringProperty codigo;
    private SimpleStringProperty descricao;
    private SimpleStringProperty cadastrado;

    public ProdutosRfidMDL(String codigo, String descricao, String cadastrado) {
        this.codigo = new SimpleStringProperty(codigo);
        this.descricao = new SimpleStringProperty(descricao);
        this.cadastrado = new SimpleStringProperty(cadastrado);
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

    public String getCadastrado() {
        return cadastrado.get();
    }

    public SimpleStringProperty cadastradoProperty() {
        return cadastrado;
    }

    public void setCadastrado(String cadastrado) {
        this.cadastrado.set(cadastrado);
    }
}
