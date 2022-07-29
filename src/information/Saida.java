package information;

import business.SaidaProdutosBLL;
import util.Date;
import util.RegistroIncompletoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amanda on 05/12/2016.
 */
public class Saida {

    private int saida_id;
    private Empresa empresa = new Empresa();
    private Date data;
    private List<SaidaProdutos> saidaProdutos = new ArrayList<>();

    public int getSaida_id() {
        return saida_id;
    }

    public void setSaida_id(int saida_id) {
        this.saida_id = saida_id;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<SaidaProdutos> getSaidaProdutos() {
        return saidaProdutos;
    }

    public void setSaidaProdutos(List<SaidaProdutos> saidaProdutos) {
        this.saidaProdutos = saidaProdutos;
    }

    public void addSaidaProdutos(SaidaProdutos saidaProdutos) {
        this.saidaProdutos.add(saidaProdutos);
    }

    public void commit() throws SQLException, RegistroIncompletoException {
        new SaidaProdutosBLL().commit(this);
    }
}
