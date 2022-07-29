package information;

import business.EmpresaBLL;
import enums.Funcao;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 29/11/2016.
 */
public class Empresa implements Information {

    private String cnpj;
    private String razao_social;
    private String nome_fantasia;
    private boolean matriz;
    private String endereco;
    private String cidade;
    private String uf;
    private Funcao modo = Funcao.INCLUSAO;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazao_social() {
        return razao_social;
    }

    public void setRazao_social(String razao_social) {
        this.razao_social = razao_social;
    }

    public String getNome_fantasia() {
        return nome_fantasia;
    }

    public void setNome_fantasia(String nome_fantasia) {
        this.nome_fantasia = nome_fantasia;
    }

    public boolean isMatriz() {
        return matriz;
    }

    public void setMatriz(boolean matriz) {
        this.matriz = matriz;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Funcao getModo() {
        return modo;
    }

    public void setModo(Funcao modo) {
        this.modo = modo;
    }

    @Override
    public void commit() throws SQLException, RegistroIncompletoException {
        new EmpresaBLL().commit(this);
    }

    @Override
    public void delete() throws SQLException, RegistroIncompletoException {
        new EmpresaBLL().delete(this);
    }

    public ObservableList<Empresa> getAll() throws SQLException {
        return new EmpresaBLL().getAll();
    }

    public Empresa get(String cnpj) throws SQLException {
        return new EmpresaBLL().get(cnpj);
    }

    public boolean temMatrizCadastrada() throws SQLException {
        return new EmpresaBLL().temMatrizCadastrada();
    }

    @Override
    public String toString() {
        return this.cnpj + " - " + this.getRazao_social();
    }
}
