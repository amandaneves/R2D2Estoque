package data;

import application.R2D2Estoque;
import information.Rfid;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Amanda on 04/12/2016.
 */
public class RfidDAO {

    public void insert(Rfid rfid)throws SQLException {
        String query = "INSERT INTO rfid (path) values (?)";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, rfid.getPath());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public void update(Rfid rfid)throws SQLException{
        String query = "UPDATE rfid SET path = ?";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            cmd.setString(1, rfid.getPath());
            cmd.executeUpdate();
            R2D2Estoque.Conexao.commit();
        } catch (SQLException e){
            R2D2Estoque.Conexao.rollback();
        }
    }

    public File get()throws SQLException{
        String query = "SELECT * FROM rfid";
        File path = null;
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            try(ResultSet result = cmd.executeQuery()){
                if(result.next()){
                    path = new File(result.getString("path"));
                }
            }
        }
        return path;
    }

    public boolean jaTemPath()throws SQLException{
        boolean retorno = false;
        String query = "SELECT * FROM rfid";
        try(PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement(query)){
            try(ResultSet result = cmd.executeQuery()){
                if(result.next()){
                    retorno = true;
                }
            }
        }
        return retorno;
    }

}
