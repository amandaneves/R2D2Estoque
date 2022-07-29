package information;

/**
 * Created by Amanda on 30/11/2016.
 */
public class UsuarioAcesso {

    private boolean usuarios;
    private boolean produtos;
    private boolean categorias;
    private boolean empresas;
    private boolean rfid;
    private boolean entrada;
    private boolean saida;

    public boolean isUsuarios() {
        return usuarios;
    }

    public void setUsuarios(boolean usuarios) {
        this.usuarios = usuarios;
    }

    public boolean isProdutos() {
        return produtos;
    }

    public void setProdutos(boolean produtos) {
        this.produtos = produtos;
    }

    public boolean isCategorias() {
        return categorias;
    }

    public void setCategorias(boolean categorias) {
        this.categorias = categorias;
    }

    public boolean isEmpresas() {
        return empresas;
    }

    public void setEmpresas(boolean empresas) {
        this.empresas = empresas;
    }

    public boolean isRfid() {
        return rfid;
    }

    public void setRfid(boolean rfid) {
        this.rfid = rfid;
    }

    public boolean isEntrada() {
        return entrada;
    }

    public void setEntrada(boolean entrada) {
        this.entrada = entrada;
    }

    public boolean isSaida() {
        return saida;
    }

    public void setSaida(boolean saida) {
        this.saida = saida;
    }
}
