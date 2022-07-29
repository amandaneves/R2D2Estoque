package util;

import application.R2D2Estoque;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Amanda on 28/11/2016.
 */
public class Date {

    private java.sql.Date timestamp;

    public Date fromDBServer() throws SQLException {
        try (PreparedStatement cmd = R2D2Estoque.Conexao.prepareStatement("SELECT now() as now")) {
            try (ResultSet result = cmd.executeQuery()) {
                if (result.next()) {
                    this.timestamp = result.getDate("now");
                }
            }
        }
        return this;
    }

    public Date() {
        // TODO Auto-generated constructor stub
    }

    public Date(String date) {
        if (date == null || date.equals("")) {
            this.timestamp = null;
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.timestamp = new java.sql.Date(((java.util.Date) formatter.parse(date)).getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date(java.sql.Date date) {
        try {
            timestamp = date;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Date(java.util.Date date) {
        try {
            this.timestamp = (java.sql.Date) new java.sql.Date(date.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String toString(String StringDateFormat) {
        if (timestamp == null) {
            return "";
        }
        return new SimpleDateFormat(StringDateFormat).format(timestamp);
    }

    public String toString() {
        if (timestamp == null) {
            return "";
        }
        return new SimpleDateFormat("dd/MM/yyyy").format(timestamp);
    }

    public java.util.Date getDate() {
        java.util.Date date = null;
        try {
            date = new java.util.Date(timestamp.getTime());
        } catch (Exception e) {
            throw e;
        }
        return date;
    }

    public java.sql.Date toSql() {
        try {
            return timestamp;
        } catch (Exception e) {
            throw e;
        }
    }

    public int getDay() {
        return Integer.parseInt(this.toString("dd"));
    }

    public int getMonth() {
        return Integer.parseInt(this.toString("MM"));
    }

    public int getYear() {
        return Integer.parseInt(this.toString("yyyy"));
    }

    public Date addDays(int day) {
        Calendar calend = Calendar.getInstance();
        calend.setTime(this.getDate());
        calend.add(5, day);
        this.timestamp = new java.sql.Date(calend.getTimeInMillis());
        return this;
    }

}
