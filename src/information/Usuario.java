package information;

import business.UsuarioBLL;
import enums.Funcao;
import enums.Genero;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 30/11/2016.
 */
public class Usuario implements Information {

    private int usuario_id;
    private String nome;
    private String telefone;
    private Genero sexo;
    private String email;
    private UsuarioAcesso usuarioAcesso = new UsuarioAcesso();
    private UsuarioConta usuarioConta = new UsuarioConta();
    private Funcao modo = Funcao.INCLUSAO ;

    public int getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(int usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Genero getSexo() {
        return sexo;
    }

    public void setSexo(Genero sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UsuarioConta getUsuarioConta() {
        return usuarioConta;
    }

    public void setUsuarioConta(UsuarioConta usuarioConta) {
        this.usuarioConta = usuarioConta;
    }

    public UsuarioAcesso getUsuarioAcesso() {
        return usuarioAcesso;
    }

    public void setUsuarioAcesso(UsuarioAcesso usuarioAcesso) {
        this.usuarioAcesso = usuarioAcesso;
    }

    public Funcao getModo() {
        return modo;
    }

    public void setModo(Funcao modo) {
        this.modo = modo;
    }

    @Override
    public void commit() throws SQLException, RegistroIncompletoException {
        new UsuarioBLL().commit(this);
    }

    @Override
    public void delete() throws SQLException, RegistroIncompletoException {
        new UsuarioBLL().delete(this);
    }

    public ObservableList<Usuario> getAll() throws SQLException {
        return new UsuarioBLL().getAll();
    }

    public Usuario get(int usuario_id) throws SQLException {
        return new UsuarioBLL().get(usuario_id);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}
