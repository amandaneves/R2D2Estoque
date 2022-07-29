package information;

import java.math.BigDecimal;

/**
 * Created by Amanda on 05/12/2016.
 */
public class SaidaProdutos {

    private int saida_produtos_id;
    private int saida_id;
    private Produto produto = new Produto();
    private BigDecimal pr_venda;
    private BigDecimal quantidade;

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPr_venda() {
        return pr_venda;
    }

    public void setPr_venda(BigDecimal pr_venda) {
        this.pr_venda = pr_venda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getSaida_id() {
        return saida_id;
    }

    public void setSaida_id(int saida_id) {
        this.saida_id = saida_id;
    }

    public int getSaida_produtos_id() {
        return saida_produtos_id;
    }

    public void setSaida_produtos_id(int saida_produtos_id) {
        this.saida_produtos_id = saida_produtos_id;
    }
}
