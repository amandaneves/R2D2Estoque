package information;

import business.ProdutoBLL;
import enums.Funcao;
import javafx.collections.ObservableList;
import util.Date;
import util.RegistroIncompletoException;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * Created by Amanda on 28/11/2016.
 */
public class Produto implements Information {

    private int produto_id;
    private String rfid;
    private String descricao;
    private Categoria categoria = new Categoria();
    private Date dt_inventario;
    private BigDecimal pr_custo;
    private BigDecimal pr_medio;
    private BigDecimal pr_venda;
    private Funcao modo = Funcao.INCLUSAO;

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Date getDt_inventario() {
        return dt_inventario;
    }

    public void setDt_inventario(Date dt_inventario) {
        this.dt_inventario = dt_inventario;
    }

    public BigDecimal getPr_custo() {
        return pr_custo;
    }

    public void setPr_custo(BigDecimal pr_custo) {
        this.pr_custo = pr_custo;
    }

    public BigDecimal getPr_medio() {
        return pr_medio;
    }

    public void setPr_medio(BigDecimal pr_medio) {
        this.pr_medio = pr_medio;
    }

    public BigDecimal getPr_venda() {
        return pr_venda;
    }

    public void setPr_venda(BigDecimal pr_venda) {
        this.pr_venda = pr_venda;
    }

    public Funcao getModo() {
        return modo;
    }

    public void setModo(Funcao modo) {
        this.modo = modo;
    }

    @Override
    public void commit() throws SQLException, RegistroIncompletoException {
        new ProdutoBLL().commit(this);
    }

    @Override
    public void delete() throws SQLException, RegistroIncompletoException {
        new ProdutoBLL().delete(this);
    }

    public ObservableList<Produto> getAll() throws SQLException {
        return new ProdutoBLL().getAll();
    }

    public Produto get(int produto_id) throws SQLException {
        return new ProdutoBLL().get(produto_id);
    }

    public Produto get(String rfid) throws SQLException {
        return new ProdutoBLL().getByRfid(rfid);
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
