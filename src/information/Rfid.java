package information;

import business.RfidBLL;
import util.RegistroIncompletoException;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by Amanda on 04/12/2016.
 */
public class Rfid {

    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void commit() throws SQLException, RegistroIncompletoException {
        new RfidBLL().commit(this);
    }

    public File get() throws SQLException {
        return new RfidBLL().get();
    }

}
