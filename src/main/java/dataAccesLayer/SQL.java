package dataAccesLayer;

import exceptions.OurException;
import model.Aftale;
import model.AftaleListe;
import model.EKG;

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

    public void makeConnectionSQL() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
            PreparedStatement pp = myConn.prepareStatement("SELECT * FROM Aftaler WHERE TimeStart BETWEEN ? and ?;");
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
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO Aftaler (CPR, TimeStart, TimeEnd, Notat, KlinikId) values(?,?,?,?,?);");

            pp.setString(1, aftale.getCPR());  //CPR
            pp.setString(2, aftale.getTimeStart());  //starttime
            pp.setString(3, aftale.getTimeEnd());  //endtime
            pp.setString(4, aftale.getNotat());  //note
            pp.setString(5, aftale.getKlinikID()); //klinikid

            pp.execute();

            removeConnectionSQL();
        } catch (SQLException throwables) {
            OurException ex = new OurException();
            ex.setMessage("Tiden er allerede optaget.");
            throw ex;
        }
    }


    public void EKGdataInsert(EKG ekg) throws OurException {
        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO EKGData (ID, Value) values(?,?);");

            pp.setInt(1, ekg.getSessionId());  //CPR
            pp.setFloat(2, ekg.getEKGdata());  //starttime
            pp.execute();

            removeConnectionSQL();
        } catch (SQLException throwables) {
            OurException ex = new OurException();
            ex.setMessage("Tiden er allerede optaget.");
            throw ex;
        }
    }

    public Integer insertSessionIDogCPR(EKG ekg) throws OurException {
        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO SessionData (CPR) values(?);",Statement.RETURN_GENERATED_KEYS);

            pp.setString(1, ekg.getCPR());  //CPR
            pp.executeUpdate();
            ResultSet a= pp.getGeneratedKeys();
            a.next();
           Integer id= a.getInt(1);
            System.out.println(id);

            removeConnectionSQL();
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();

           /* OurException ex = new OurException();
            ex.setMessage("Tiden er allerede optaget.");
            throw ex; */
        }
        return null;
    }

    public AftaleListe getAftalerListe() throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        AftaleListe aftaleListe = new AftaleListe();
        String query = "SELECT * FROM Aftaler";
        try {
            ResultSet rs = SQL.getSqlOBJ().myStatement.executeQuery(query);

            while (rs.next()) {
                Aftale aftale = new Aftale();
                aftale.setCPR(String.valueOf(rs.getInt(2)));
                aftale.setTimeStart(rs.getString(3));
                aftale.setTimeEnd(rs.getString(4));
                aftale.setNotat(rs.getString(5));
                aftale.setID(rs.getString(1));
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
        PreparedStatement preparedStatement = myConn.prepareStatement("SELECT * FROM LoginOplysninger WHERE USERNAME = ?;");
        preparedStatement.setString(1, bruger);
        String svar = "";
        try {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                svar = svar + rs.getString(1);
                svar = svar + "|" + rs.getString(2);
                svar = svar + "|" + rs.getString(3);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();
        return svar;
    }

    public AftaleListe cprSearch(String cpr) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        PreparedStatement pp = myConn.prepareStatement("SELECT * FROM Aftaler WHERE CPR = ?;");
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




  /*  public AftaleListe getEkgMeasurements() throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        AftaleListe aftaleListe = new AftaleListe();
        String query = "SELECT * FROM NyEKGData";
        try {
            ResultSet rs = SQL.getSqlOBJ().myStatement.executeQuery(query);

            while (rs.next()) {
                Aftale aftale = new Aftale();
                aftale.setCPR(String.valueOf(rs.getInt(2)));
                System.out.println("lelele1");
                aftale.setTimeStart(rs.getString(3));
                System.out.println("lelele2");
                aftaleListe.addAftaler(aftale);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();


        return aftaleListe;
    }
*/

}

