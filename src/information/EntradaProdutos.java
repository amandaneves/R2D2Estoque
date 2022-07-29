package information;

import java.math.BigDecimal;

/**
 * Created by Amanda on 04/12/2016.
 */
public class EntradaProdutos {

    private int entrada_produtos_id;
    private int entrada_id;
    private Produto produto = new Produto();
    private BigDecimal pr_custo;
    private BigDecimal quantidade;

    public int getEntrada_produtos_id() {
        return entrada_produtos_id;
    }

    public void setEntrada_produtos_id(int entrada_produtos_id) {
        this.entrada_produtos_id = entrada_produtos_id;
    }

    public int getEntrada_id() {
        return entrada_id;
    }

    public void setEntrada_id(int entrada_id) {
        this.entrada_id = entrada_id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getPr_custo() {
        return pr_custo;
    }

    public void setPr_custo(BigDecimal pr_custo) {
        this.pr_custo = pr_custo;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
