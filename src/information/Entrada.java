package information;

import business.EntradaProdutosBLL;
import util.Date;
import util.RegistroIncompletoException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amanda on 04/12/2016.
 */
public class Entrada {

    private int entrada_id;
    private Empresa empresa = new Empresa();
    private Date data;
    private List<EntradaProdutos> entradaProdutos = new ArrayList<>();

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public int getEntrada_id() {
        return entrada_id;
    }

    public void setEntrada_id(int entrada_id) {
        this.entrada_id = entrada_id;
    }

    public List<EntradaProdutos> getEntradaProdutos() {
        return entradaProdutos;
    }

    public void setEntradaProdutos(List<EntradaProdutos> entradaProdutos) {
        this.entradaProdutos = entradaProdutos;
    }

    public void addEntradaProdutos(EntradaProdutos entradaProdutos) {
        this.entradaProdutos.add(entradaProdutos);
    }

    public void commit() throws SQLException, RegistroIncompletoException {
        new EntradaProdutosBLL().commit(this);
    }
}
