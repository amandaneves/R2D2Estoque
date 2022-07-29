package business;

import data.UsuarioDAO;
import enums.Funcao;
import information.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 30/11/2016.
 */
public class UsuarioBLL {

    public void commit(Usuario usuario) throws SQLException, RegistroIncompletoException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        if (usuario.getNome().trim().isEmpty()) {
            mensagens.add("Informe o NOME do usuário.");
        }

        if (usuario.getTelefone().trim().isEmpty()) {
            mensagens.add("Informe o TELEFONE do usuário.");
        }

        if (usuario.getUsuarioConta().getUsername().trim().isEmpty()) {
            mensagens.add("Informe o USERNAME do usuário.");
        } else if (new UsuarioDAO().temEsseUsernameCadastrado(usuario)) {
            mensagens.add("Já existe este USERNAME cadastrado para outro usuário.");
        }

        if (usuario.getUsuarioConta().getSenha().trim().isEmpty()) {
            mensagens.add("Informe a SENHA do usuário.");
        } else if(!usuario.getUsuarioConta().getSenha().equals(usuario.getUsuarioConta().getConfirmaSenha())){
            mensagens.add("Os campos SENHA e CONFIRMA SENHA não conferem.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }

        if (usuario.getModo() == Funcao.INCLUSAO) {
            new UsuarioDAO().insert(usuario);
        } else {
            new UsuarioDAO().update(usuario);
        }
    }

    public ObservableList<Usuario> getAll() throws SQLException {
        return new UsuarioDAO().getAll();
    }

    public Usuario get(int usuario_id) throws SQLException {
        return new UsuarioDAO().get(usuario_id);
    }

    public void delete(Usuario usuario) throws SQLException, RegistroIncompletoException {
        try {
            new UsuarioDAO().delete(usuario);
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23503") == 0) {
                throw new RegistroIncompletoException("Registro em uso, exclusão não permitida!");
            }
            throw e;
        }
    }

}
