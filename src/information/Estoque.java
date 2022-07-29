package information;

import java.math.BigDecimal;

/**
 * Created by Amanda on 04/12/2016.
 */
public class Estoque {

    private Produto produto = new Produto();
    private Empresa empresa = new Empresa();
    private BigDecimal quantidade;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
