package data;

import application.R2D2Estoque;
import enums.Genero;
import information.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 30/11/2016.
 */
public class UsuarioDAO {

    public void insert(Usuario usuario) throws SQLException {
        String queryUsuario = "INSERT INTO usuarios (nome, telefone, sexo, email) VALUES (?, ?, ?, ?)";
        String queryUsuarioAcesso = "INSERT INTO usuarios_acesso (usuario_id, usuarios, produtos, categorias, empresas, rfid, " +
                "entrada, saida) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String queryUsuarioConta = "INSERT INTO usuarios_conta (username, usuario_id, senha) VALUES (?, ?, ?)";
        try {

            PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(queryUsuario);
            cmd.setString(1, usuario.getNome());
            cmd.setString(2, usuario.getTelefone());
            cmd.setString(3, usuario.getSexo().getLetra());
            cmd.setString(4, usuario.getEmail());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();

            PreparedStatement cmdUsuario = R2D2Estoque.Conexao.prepareStatement("SELECT MAX(usuario_id) as usuario_id FROM usuarios");
            ResultSet result = cmdUsuario.executeQuery();
            if (result.next()) {
                usuario.setUsuario_id(result.getInt("usuario_id"));
            }

            PreparedStatement cmdUsuarioAcesso = R2D2Estoque.Conexao.prepareStatement(queryUsuarioAcesso);
            cmdUsuarioAcesso.setInt(1, usuario.getUsuario_id());
            cmdUsuarioAcesso.setBoolean(2, usuario.getUsuarioAcesso().isUsuarios());
            cmdUsuarioAcesso.setBoolean(3, usuario.getUsuarioAcesso().isProdutos());
            cmdUsuarioAcesso.setBoolean(4, usuario.getUsuarioAcesso().isCategorias());
            cmdUsuarioAcesso.setBoolean(5, usuario.getUsuarioAcesso().isEmpresas());
            cmdUsuarioAcesso.setBoolean(6, usuario.getUsuarioAcesso().isRfid());
            cmdUsuarioAcesso.setBoolean(7, usuario.getUsuarioAcesso().isEntrada());
            cmdUsuarioAcesso.setBoolean(8, usuario.getUsuarioAcesso().isSaida());
            cmdUsuarioAcesso.executeUpdate();

            PreparedStatement cmdUsuarioConta = R2D2Estoque.Conexao.prepareStatement(queryUsuarioConta);
            cmdUsuarioConta.setString(1, usuario.getUsuarioConta().getUsername());
            cmdUsuarioConta.setInt(2, usuario.getUsuario_id());
            cmdUsuarioConta.setString(3, usuario.getUsuarioConta().getSenha());
            cmdUsuarioConta.executeUpdate();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            R2D2Estoque.Conexao.rollback();
        }
    }

    public void update(Usuario usuario) throws SQLException {
        String queryUsuario = "UPDATE usuarios SET nome = ?, telefone = ?, sexo = ?, email = ? WHERE usuario_id = ?";
        String queryUsuarioAcesso = "UPDATE usuarios_acesso SET usuarios = ?, produtos = ?, categorias = ?, empresas = ?, rfid = ?, " +
                "entrada = ?, saida = ? WHERE usuario_id = ?";
        String queryUsuarioConta = "UPDATE usuarios_conta SET username = ?, senha = ? WHERE usuario_id = ?";
        try {
            PreparedStatement cmdUsuario = R2D2Estoque.Conexao.prepareStatement(queryUsuario);
            cmdUsuario.setString(1, usuario.getNome());
            cmdUsuario.setString(2, usuario.getTelefone());
            cmdUsuario.setString(3, usuario.getSexo().getLetra());
            cmdUsuario.setString(4, usuario.getEmail());
            cmdUsuario.setInt(5, usuario.getUsuario_id());
            cmdUsuario.executeUpdate();

            PreparedStatement cmdUsuarioAcesso = R2D2Estoque.Conexao.prepareStatement(queryUsuarioAcesso);
            cmdUsuarioAcesso.setBoolean(1, usuario.getUsuarioAcesso().isUsuarios());
            cmdUsuarioAcesso.setBoolean(2, usuario.getUsuarioAcesso().isProdutos());
            cmdUsuarioAcesso.setBoolean(3, usuario.getUsuarioAcesso().isCategorias());
            cmdUsuarioAcesso.setBoolean(4, usuario.getUsuarioAcesso().isEmpresas());
            cmdUsuarioAcesso.setBoolean(5, usuario.getUsuarioAcesso().isRfid());
            cmdUsuarioAcesso.setBoolean(6, usuario.getUsuarioAcesso().isEntrada());
            cmdUsuarioAcesso.setBoolean(7, usuario.getUsuarioAcesso().isSaida());
            cmdUsuarioAcesso.setInt(8, usuario.getUsuario_id());
            cmdUsuarioAcesso.executeUpdate();

            PreparedStatement cmdUsuarioConta = R2D2Estoque.Conexao.prepareStatement(queryUsuarioConta);
            cmdUsuarioConta.setString(1, usuario.getUsuarioConta().getUsername());
            cmdUsuarioConta.setString(2, usuario.getUsuarioConta().getSenha());
            cmdUsuarioConta.setInt(3, usuario.getUsuario_id());
            cmdUsuarioConta.executeUpdate();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }
    }

    public ObservableList<Usuario> getAll() throws SQLException {
        String query = "SELECT * FROM usuarios WHERE usuario_id > 1";
        Usuario usuario = null;
        ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            try (ResultSet result = cmd.executeQuery()) {
                while (result.next()) {
                    usuario = new Usuario();
                    usuario.setUsuario_id(result.getInt("usuario_id"));
                    usuario.setNome(result.getString("nome"));
                    usuario.setTelefone(result.getString("telefone"));
                    usuario.setSexo(Genero.getGenero(result.getString("sexo")));
                    usuario.setEmail(result.getString("email"));
                    usuarios.add(usuario);
                }
            }
        }
        return usuarios;
    }

    public Usuario get(int usuario_id) throws SQLException {
        String queryUsuario = "SELECT * FROM usuarios WHERE usuario_id = ?";
        String queryUsuarioAcesso = "SELECT * FROM usuarios_acesso WHERE usuario_id = ?";
        String queryUsuarioConta = "SELECT * FROM usuarios_conta WHERE usuario_id = ?";
        Usuario usuario = new Usuario();

        PreparedStatement cmdUsuario = R2D2Estoque.Conexao.prepareStatement(queryUsuario);
        cmdUsuario.setInt(1, usuario_id);
        ResultSet resultUsuario = cmdUsuario.executeQuery();
        if (resultUsuario.next()) {
            usuario.setUsuario_id(resultUsuario.getInt("usuario_id"));
            usuario.setNome(resultUsuario.getString("nome"));
            usuario.setTelefone(resultUsuario.getString("telefone"));
            usuario.setSexo(Genero.getGenero(resultUsuario.getString("sexo")));
            usuario.setEmail(resultUsuario.getString("email"));
        }

        PreparedStatement cmdUsuarioAcesso = R2D2Estoque.Conexao.prepareStatement(queryUsuarioAcesso);
        cmdUsuarioAcesso.setInt(1, usuario_id);
        ResultSet resultUsuarioAcesso = cmdUsuarioAcesso.executeQuery();
        if (resultUsuarioAcesso.next()) {
            usuario.getUsuarioAcesso().setUsuarios(resultUsuarioAcesso.getBoolean("usuarios"));
            usuario.getUsuarioAcesso().setProdutos(resultUsuarioAcesso.getBoolean("produtos"));
            usuario.getUsuarioAcesso().setCategorias(resultUsuarioAcesso.getBoolean("categorias"));
            usuario.getUsuarioAcesso().setEmpresas(resultUsuarioAcesso.getBoolean("empresas"));
            usuario.getUsuarioAcesso().setRfid(resultUsuarioAcesso.getBoolean("rfid"));
            usuario.getUsuarioAcesso().setEntrada(resultUsuarioAcesso.getBoolean("entrada"));
            usuario.getUsuarioAcesso().setSaida(resultUsuarioAcesso.getBoolean("saida"));
        }

        PreparedStatement cmdUsuarioConta = R2D2Estoque.Conexao.prepareStatement(queryUsuarioConta);
        cmdUsuarioConta.setInt(1, usuario_id);
        ResultSet resultUsuarioConta = cmdUsuarioConta.executeQuery();
        if (resultUsuarioConta.next()) {
            usuario.getUsuarioConta().setUsername(resultUsuarioConta.getString("username"));
            usuario.getUsuarioConta().setSenha(resultUsuarioConta.getString("senha"));
        }

        resultUsuario.close();
        resultUsuarioAcesso.close();
        resultUsuarioConta.close();

        return usuario;
    }

    public void delete(Usuario usuario) throws SQLException {
        String queryUsuarioAcesso = "DELETE FROM usuarios_acesso WHERE usuario_id = ?";
        String queryUsuarioConta = "DELETE FROM usuarios_conta WHERE usuario_id = ?";
        String queryUsuario = "DELETE FROM usuarios WHERE usuario_id = ?";

        try {
            PreparedStatement cmdUsuarioAcesso = R2D2Estoque.Conexao.prepareStatement(queryUsuarioAcesso);
            cmdUsuarioAcesso.setInt(1, usuario.getUsuario_id());
            cmdUsuarioAcesso.executeUpdate();

            PreparedStatement cmdUsuarioConta = R2D2Estoque.Conexao.prepareStatement(queryUsuarioConta);
            cmdUsuarioConta.setInt(1, usuario.getUsuario_id());
            cmdUsuarioConta.executeUpdate();

            PreparedStatement cmdUsuario = R2D2Estoque.Conexao.prepareStatement(queryUsuario);
            cmdUsuario.setInt(1, usuario.getUsuario_id());
            cmdUsuario.executeUpdate();

            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }

    }

    public boolean temEsseUsernameCadastrado(Usuario usuario) throws SQLException {
        boolean retorno = false;
        String query = "SELECT usuario_id FROM usuarios_conta WHERE username = ? and usuario_id <> ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, usuario.getUsuarioConta().getUsername());
            cmd.setInt(2, usuario.getUsuario_id());
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public boolean temEsseUsernameCadastrado(String username) throws SQLException {
        boolean retorno = false;
        String query = "SELECT usuario_id FROM usuarios_conta WHERE username = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, username);
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public boolean senhaConfere(String username, String senha) throws SQLException {
        boolean retorno = false;
        String query = "SELECT senha FROM usuarios_conta WHERE username = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, username);
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    if(result.getString("senha").equals(senha))
                        retorno = true;
                }
            }
        }
        return retorno;
    }

}
