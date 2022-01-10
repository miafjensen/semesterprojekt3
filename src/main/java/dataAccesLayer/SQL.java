package dataAccesLayer;

import exceptions.OurException;
import model.Aftale;
import model.AftaleListe;

import java.sql.*;

public class SQL {

    private SQL() {
    }

    static private final SQL SQLOBJ = new SQL();

    static public SQL getSqlOBJ() {
        return SQLOBJ;
    }

    private final String url = "jdbc:mysql://mysql-db.caprover.diplomportal.dk:3306/s190600?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final String DatabaseUser = "s190600";
    private final String DatabasePassword = "Qd5UiHM09iNxfubw7OWnC"; //tomcat system startups

    private Connection myConn;
    public Statement myStatement;

    public static Connection getConnection(){
// nu med vores server
        Connection con = null;
        String url = "jdbc:mysql://mysql-db.caprover.diplomportal.dk:3306/s190600?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String DatabaseUser = "s190600";
        String DatabasePassword = "Qd5UiHM09iNxfubw7OWnC"; //tomcat system startups

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, DatabaseUser, DatabasePassword);
            System.out.println("forbindelse til db oprette");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Fejl i forbindelse til db");
        }
        return con;
    }

    public void makeConnectionSQL() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("forbindelse til db oprette");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Fejl i forbindelse til db");
        }
        myConn = DriverManager.getConnection(url, DatabaseUser, DatabasePassword);
        myStatement = myConn.createStatement();

    }

    public void removeConnectionSQL() {
        try {
            if (!myConn.isClosed()) {
                myConn.close();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public AftaleListe getAftaleListeDateTime(String fra, String til) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        AftaleListe aftaleListe = new AftaleListe();
        try {
            PreparedStatement pp = myConn.prepareStatement("SELECT * FROM aftaler WHERE TimeStart BETWEEN ? and ?;");
            pp.setString(1, fra);
            pp.setString(2, til);

            ResultSet rs = pp.executeQuery();

            while (rs.next()) {
                Aftale aftale = new Aftale();
                aftale.setCPR(String.valueOf(rs.getInt(1)));
                aftale.setTimeStart(rs.getString(2));
                aftale.setTimeEnd(rs.getString(3));
                aftale.setNotat(rs.getString(4));
                aftale.setID(rs.getString(5));
                aftale.setKlinikID(rs.getString(6));


                aftaleListe.addAftaler(aftale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();

        return aftaleListe;
    }

    public void insertAftaleSQL(Aftale aftale) throws OurException {

        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO aftaler (CPR, TimeStart, TimeEnd, Notat, KlinikId) values(?,?,?,?,?);");

            pp.setString(1, aftale.getCPR());  //CPR
            pp.setString(2, aftale.getTimeStart());  //starttime
            pp.setString(3, aftale.getTimeEnd());  //endtime
            pp.setString(4, aftale.getNotat());  //note
            pp.setString(5, aftale.getKlinikID()); //klinikif

            pp.execute();

            removeConnectionSQL();
        } catch (SQLException throwables) {
            OurException ex = new OurException();
            ex.setMessage("Tiden er allerede optaget.");
            throw ex;
        }
    }

    public AftaleListe getAftalerListe() throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        AftaleListe aftaleListe = new AftaleListe();
        String query = "SELECT * FROM aftaler";
        try {
            ResultSet rs = SQL.getSqlOBJ().myStatement.executeQuery(query);

            while (rs.next()) {
                Aftale aftale = new Aftale();
                aftale.setCPR(String.valueOf(rs.getInt(1)));
                aftale.setTimeStart(rs.getString(2));
                aftale.setTimeEnd(rs.getString(3));
                aftale.setNotat(rs.getString(4));
                aftale.setID(rs.getString(5));
                aftale.setKlinikID(rs.getString(6));

                aftaleListe.addAftaler(aftale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();

        return aftaleListe;
    }

    public String hentBrugerListe(String bruger) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        PreparedStatement preparedStatement = myConn.prepareStatement("SELECT * FROM LoginOplysninger WHERE username = ?;");
        preparedStatement.setString(1, bruger);
        String svar = "";
        try {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                svar = svar + rs.getString(1);
                svar = svar + "|" + rs.getString(2);
                svar = svar + "|" + rs.getString(3);
                System.out.println("succes login!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("fejl i login");
        }
        SQL.getSqlOBJ().removeConnectionSQL();
        return svar;
    }

    public AftaleListe cprSearch(String cpr) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        PreparedStatement pp = myConn.prepareStatement("SELECT * FROM aftaler WHERE CPR = ?;");
        AftaleListe aftaleListe = new AftaleListe();
        try {
            pp.setString(1, cpr);
            ResultSet rs = pp.executeQuery();

            while (rs.next()) {
                Aftale aftale = new Aftale();
                aftale.setCPR(String.valueOf(rs.getInt(1)));
                aftale.setTimeStart(rs.getString(2));
                aftale.setTimeEnd(rs.getString(3));
                aftale.setNotat(rs.getString(4));
                aftale.setID(rs.getString(5));
                aftale.setKlinikID(rs.getString(6));

                aftaleListe.addAftaler(aftale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();
        return aftaleListe;
    }
}

