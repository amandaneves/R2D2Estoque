package business;

import data.EmpresaDAO;
import enums.Funcao;
import information.Empresa;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 29/11/2016.
 */
public class EmpresaBLL {

    public void commit(Empresa empresa) throws SQLException, RegistroIncompletoException {
        ObservableList<String> mensagens = FXCollections.observableArrayList();

        if (empresa.getCnpj().trim().isEmpty()) {
            mensagens.add("Informe o CNPJ da empresa.");
        }

        if (empresa.getRazao_social().trim().isEmpty()) {
            mensagens.add("Informe a RAZÃO SOCIAL da empresa.");
        }

        if (empresa.getNome_fantasia().trim().isEmpty()) {
            mensagens.add("Informe o NOME FANTASIA da empresa.");
        }

        if (empresa.getCidade().trim().isEmpty()) {
            mensagens.add("Informe a CIDADE da empresa");
        }

        if (empresa.getUf().trim().isEmpty()) {
            mensagens.add("Informe a UF da empresa.");
        }

        if (empresa.getModo() == Funcao.INCLUSAO && new EmpresaDAO().temEsseCnpjCadastrado(empresa)) {
            mensagens.add("Já existe uma empresa com este CNPJ cadastrado no sistema.");
        }

        if (mensagens.size() > 0) {
            throw new RegistroIncompletoException(mensagens);
        }

        if (empresa.getModo() == Funcao.INCLUSAO) {
            new EmpresaDAO().insert(empresa);
        } else {
            new EmpresaDAO().update(empresa);
        }
    }

    public ObservableList<Empresa> getAll() throws SQLException {
        return new EmpresaDAO().getAll();
    }

    public Empresa get(String cnpj) throws SQLException {
        return new EmpresaDAO().get(cnpj);
    }

    public boolean temMatrizCadastrada() throws SQLException {
        return new EmpresaDAO().temMatrizCadastrada();
    }

    public void delete(Empresa empresa) throws SQLException, RegistroIncompletoException {
        try {
            new EmpresaDAO().delete(empresa);
        } catch (SQLException e) {
            if (e.getSQLState().compareTo("23503") == 0) {
                throw new RegistroIncompletoException("Registro em uso, exclusão não permitida!");
            }
            throw e;
        }
    }

}
