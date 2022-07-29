package information;

import util.RegistroIncompletoException;

import java.sql.SQLException;

/**
 * Created by Amanda on 27/11/2016.
 */
public interface Information {

    void commit() throws SQLException, RegistroIncompletoException;

    void delete() throws SQLException, RegistroIncompletoException;
}
