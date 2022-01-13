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


    public void EKGdataInsert(int id, double datapoint) throws OurException {
        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO EKGData (SessionID, Value) values(?,?);");

            pp.setInt(1, id);  //CPR
            pp.setDouble(2, datapoint);  //starttime
            pp.execute();

            removeConnectionSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void EKGdataInsertBatch(int id, double[] datapointBatch) throws OurException {
        try {
            makeConnectionSQL();
            System.out.println(System.currentTimeMillis());
            myConn.setAutoCommit(false);
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO EKGData (SessionID, Value) values(?,?);");

            for (int i = 0; i < datapointBatch.length; i++) {
                pp.setInt(1, id);  //CPR
                pp.setDouble(2, datapointBatch[i]);  //starttime
                pp.addBatch();
            }
            System.out.println(System.currentTimeMillis());
            pp.executeLargeBatch();
            myConn.commit();
            myConn.setAutoCommit(true);
            System.out.println(System.currentTimeMillis());
            removeConnectionSQL();
            System.out.println("Batch sendt til EKGData");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Integer createEKGSession(String cpr) throws OurException {
        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO SessionData (CPR) values(?);", Statement.RETURN_GENERATED_KEYS);

            pp.setString(1, cpr);  //CPR
            pp.executeUpdate();
            ResultSet a = pp.getGeneratedKeys();
            a.next();
            Integer id = a.getInt(1);
            System.out.println(id);

            removeConnectionSQL();
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return null;
    }

    public String getSessionID(String cpr) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();

        PreparedStatement pp = myConn.prepareStatement("SELECT start, SessionID FROM SessionsData WHERE CPR = ?");
        pp.setString(1, cpr);
        String dato = "";
        try {
            ResultSet rs = pp.executeQuery();
            while (rs.next()) {
                dato = dato + rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        SQL.getSqlOBJ().removeConnectionSQL();
        return dato;
    }

    public void getEKGData(int sesID) throws SQLException{
        SQL.getSqlOBJ().makeConnectionSQL();
        PreparedStatement pp = myConn.prepareStatement("SELECT Value FROM EKGData WHERE SessionID = ?");
        pp.setInt(1, sesID);

        try{
            ResultSet rs = pp.executeQuery();
            while (rs.next()){
                // hvordan tager man ud i batch?
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        SQL.getSqlOBJ().removeConnectionSQL();
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


}

