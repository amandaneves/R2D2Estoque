package util;

import javafx.beans.property.SimpleStringProperty;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private SimpleStringProperty host;

    public String getHost() {
        return host.get();
    }

    public SimpleStringProperty hostProperty() {
        return host;
    }

    public void setHost(String host) {
        this.host.set(host);
    }

    public String getBanco() {
        return banco.get();
    }

    public SimpleStringProperty bancoProperty() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco.set(banco);
    }

    public String getPorta() {
        return porta.get();
    }

    public SimpleStringProperty portaProperty() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta.set(porta);
    }

    public String getUsuario() {
        return usuario.get();
    }

    public SimpleStringProperty usuarioProperty() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario.set(usuario);
    }

    public String getSenha() {
        return senha.get();
    }

    public SimpleStringProperty senhaProperty() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha.set(senha);
    }

    public String getString() {
        return "jdbc:mysql://" + getHost() + ":" + getPorta() + "/" + getBanco() + "?user=" + getUsuario() + "&password=" + getSenha() + "&serverTimezone=UTC";
    }

    public Connection getConexao() {
        try {
            conectar();
        } catch (Exception ex) {

        }
        return this.conexao;
    }

    private SimpleStringProperty banco;
    private SimpleStringProperty porta;
    private SimpleStringProperty usuario;
    private SimpleStringProperty senha;
    private Connection conexao;

    public Conexao() {
        try {
            this.host = new SimpleStringProperty("localhost");
            this.banco = new SimpleStringProperty("r2d2estoque");
            this.porta = new SimpleStringProperty("3306");
            this.usuario = new SimpleStringProperty("root");
            this.senha = new SimpleStringProperty("password");
        } catch (Exception ex) {

        }
    }

    public void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            this.conexao = DriverManager.getConnection(getString(), getUsuario(), getSenha());
            this.conexao.setAutoCommit(false);
        } catch (Exception ex) {
            String msg = ex.getMessage();
        }
    }
}
