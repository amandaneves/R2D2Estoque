package data;

import application.R2D2Estoque;
import information.Empresa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 29/11/2016.
 */
public class EmpresaDAO {

    public void insert(Empresa empresa) throws SQLException {
        String query = "INSERT INTO empresas (cnpj, razao_social, nome_fantasia, matriz, endereco, cidade, uf) VALUES " +
                "(?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, empresa.getCnpj());
            cmd.setString(2, empresa.getRazao_social());
            cmd.setString(3, empresa.getNome_fantasia());
            cmd.setBoolean(4, empresa.isMatriz());
            cmd.setString(5, empresa.getEndereco());
            cmd.setString(6, empresa.getCidade());
            cmd.setString(7, empresa.getUf());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }
    }

    public void update(Empresa empresa) throws SQLException {
        String query = "UPDATE empresas SET razao_social = ?, nome_fantasia = ?, matriz = ?, endereco = ?, cidade = ?, " +
                "uf = ? WHERE cnpj = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, empresa.getRazao_social());
            cmd.setString(2, empresa.getNome_fantasia());
            cmd.setBoolean(3, empresa.isMatriz());
            cmd.setString(4, empresa.getEndereco());
            cmd.setString(5, empresa.getCidade());
            cmd.setString(6, empresa.getUf());
            cmd.setString(7, empresa.getCnpj());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }
    }

    public ObservableList<Empresa> getAll() throws SQLException {
        String query = "SELECT * FROM empresas";
        ObservableList<Empresa> empresas = FXCollections.observableArrayList();
        Empresa empresa = null;
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            try (ResultSet result = cmd.executeQuery()) {
                while (result.next()) {
                    empresa = new Empresa();
                    empresa.setCnpj(result.getString("cnpj"));
                    empresa.setRazao_social(result.getString("razao_social"));
                    empresa.setNome_fantasia(result.getString("nome_fantasia"));
                    empresa.setMatriz(result.getBoolean("matriz"));
                    empresa.setEndereco(result.getString("endereco"));
                    empresa.setCidade(result.getString("cidade"));
                    empresa.setUf(result.getString("uf"));
                    empresas.add(empresa);
                }
            }
        }
        return empresas;
    }

    public Empresa get(String cnpj) throws SQLException {
        String query = "SELECT * FROM empresas WHERE cnpj = ?";
        Empresa empresa = new Empresa();
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, cnpj);
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    empresa.setCnpj(result.getString("cnpj"));
                    empresa.setRazao_social(result.getString("razao_social"));
                    empresa.setNome_fantasia(result.getString("nome_fantasia"));
                    empresa.setMatriz(result.getBoolean("matriz"));
                    empresa.setEndereco(result.getString("endereco"));
                    empresa.setCidade(result.getString("cidade"));
                    empresa.setUf(result.getString("uf"));
                }
            }
        }
        return empresa;
    }

    public void delete(Empresa empresa) throws SQLException {
        String query = "DELETE FROM empresas WHERE cnpj = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, empresa.getCnpj());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e) {
            R2D2Estoque.Conexao.rollback();
        }
    }

    public boolean temEsseCnpjCadastrado(Empresa empresa) throws SQLException {
        boolean retorno = false;
        String query = "SELECT cnpj FROM empresas WHERE cnpj = ?";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            cmd.setString(1, empresa.getCnpj());
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

    public boolean temMatrizCadastrada() throws SQLException {
        boolean retorno = false;
        String query = "SELECT cnpj FROM empresas WHERE matriz = true";
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)) {
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    retorno = true;
                }
            }
        }
        return retorno;
    }

}
