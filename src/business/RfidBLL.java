package business;

import data.RfidDAO;
import information.Rfid;
import util.RegistroIncompletoException;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by Amanda on 04/12/2016.
 */
public class RfidBLL {

    public void commit(Rfid rfid) throws SQLException, RegistroIncompletoException {
        if (rfid.getPath().trim().isEmpty()) {
            throw new RegistroIncompletoException("Informe o caminho do arquivo RFID.");
        }

        if (new RfidDAO().jaTemPath()) {
            new RfidDAO().update(rfid);
        } else {
            new RfidDAO().insert(rfid);
        }
    }

    public File get() throws SQLException {
        return new RfidDAO().get();
    }

}
