/**

 * @author ${IT3 grp4 tilpasset og tilføjelser af Magnus og Mia}

 * @Date ${jan 2022}

 */
package dataAccesLayer;

import model.Aftale;
import model.AftaleListe;
import model.EKG;
import model.EKGListe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQL {

    private SQL() {
    }

    static private final SQL SQLOBJ = new SQL();


    static public SQL getSqlOBJ() {
        return SQLOBJ;
    }

    private final String DatabaseUser = "s190600";
    private final String DatabasePassword = "Qd5UiHM09iNxfubw7OWnC";
    private final String url = "jdbc:mysql://mysql-db.caprover.diplomportal.dk:3306/" + DatabaseUser +"?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    private Connection myConn;
    public Statement myStatement;

    public void makeConnectionSQL() throws SQLException {
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            myConn = DriverManager.getConnection(url, DatabaseUser, DatabasePassword);
            myStatement = myConn.createStatement();
        }catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("ikke oprettet forbindelse til database");
        }
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
                aftale.setCPR(rs.getString("CPR"));
                aftale.setTimeStart(rs.getString("TimeStart"));
                aftale.setTimeEnd(rs.getString("TimeEnd"));
                aftale.setNotat(rs.getString("Notat"));
                aftale.setID(rs.getString("Id"));
                aftale.setKlinikID(rs.getString("KlinikId"));


                aftaleListe.addAftaler(aftale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();

        return aftaleListe;
    }


    public void insertAftaleSQL(Aftale aftale){

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
           throwables.printStackTrace();
        }
    }

    public AftaleListe getAftalerListe() throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        AftaleListe aftaleListe = new AftaleListe();
        String query = "SELECT * FROM Aftaler";
        try {
            ResultSet rs = SQL.getSqlOBJ().myStatement.executeQuery(query);

            while (rs.next()) {
                Aftale aftale = new Aftale();
                aftale.setCPR(String.valueOf(rs.getString("CPR")));
                aftale.setTimeStart(rs.getString("TimeStart"));
                aftale.setTimeEnd(rs.getString("TimeEnd"));
                aftale.setNotat(rs.getString("Notat"));
                aftale.setID(rs.getString("Id"));
                aftale.setKlinikID(rs.getString("KlinikId"));

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
                svar = svar + rs.getString("username");
                svar = svar + "|" + rs.getString("password");
                svar = svar + "|" + rs.getString("salt");
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
                aftale.setCPR(String.valueOf(rs.getString("CPR")));
                aftale.setTimeStart(rs.getString("TimeStart"));
                aftale.setTimeEnd(rs.getString("TimeEnd"));
                aftale.setNotat(rs.getString("Notat"));
                aftale.setID(rs.getString("Id"));
                aftale.setKlinikID(rs.getString("KlinikId"));

                aftaleListe.addAftaler(aftale);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();
        return aftaleListe;
    }

    /**

     * @author ${alt herunder er tilføjelser af Magnus og Mia}

     * @Date ${jan 2022}

     */
    public void EKGdataInsert(int id, double datapoint) {
        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO EKGData (SessionID, Value) values(?,?);");

            pp.setInt(1, id);  //SessionID
            pp.setDouble(2, datapoint);  //data fra python
            pp.execute();

            removeConnectionSQL();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void EKGdataInsertBatch(int id, double[] datapointBatch) {
        try {
            try {
                makeConnectionSQL();
                System.out.println(System.currentTimeMillis());
                myConn.setAutoCommit(false);
                PreparedStatement pp = myConn.prepareStatement("INSERT INTO EKGData (SessionID, Value) values(?,?);");

                for (int i = 0; i < datapointBatch.length; i++) {
                    pp.setInt(1, id);  //SessionID
                    pp.setDouble(2, datapointBatch[i]);  //data fra Python
                    pp.addBatch();
                }
                pp.executeBatch();
                System.out.println("Batch sendt til EKGData");

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                System.out.println("fejl i upload til db");
            }
            myConn.setAutoCommit(true);
        }catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("fejl i commit");
        }
        System.out.println(System.currentTimeMillis());
        System.out.println("forbindelse til SQL fjernet");
        removeConnectionSQL();
    }

    public Integer createEKGSession(String cpr) {
        try {
            makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("INSERT INTO SessionData (CPR) values(?);", Statement.RETURN_GENERATED_KEYS);

            pp.setString(1, cpr);  //CPR
            pp.executeUpdate();
            ResultSet a = pp.getGeneratedKeys(); //henter sessionID som er key i SessionData på nyindsat data
            a.next();
            Integer id = a.getInt(1); //gemmer sessionID
            System.out.println(id);

            removeConnectionSQL();
            return id; //sender SessionID til postEKGdata i EKGController
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return null;
    }

    public void createNewUser (String brugernavn, String password, int salt) throws SQLException {
        makeConnectionSQL();
        PreparedStatement pp = myConn.prepareStatement("INSERT INTO LoginOplysninger (username, password, salt) values (?,?,?);");
        try {
            pp.setString(1, brugernavn);
            pp.setString(2, password);
            pp.setInt(3, salt);

            pp.execute();
            removeConnectionSQL();
        }catch (SQLException throwables){
            throwables.printStackTrace();
            System.out.println("fejl i oprettelse af bruger");
        }
    }


    public  EKGListe getALLSessions() throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        EKGListe ekgListe = new EKGListe();

        try{
            PreparedStatement pp = myConn.prepareStatement("SELECT * FROM SessionData order by CPR;");
            ResultSet rs = pp.executeQuery();

            while (rs.next()){
                EKG ekg = new EKG();
                ekg.setDato(rs.getString("Start"));
                ekg.setCPR(rs.getString("CPR"));
                ekg.setSessionID(rs.getInt("SessionID"));
                System.out.println(ekg);

                ekgListe.addEKGListe(ekg);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();
        System.out.println(ekgListe);
        return ekgListe;
    }

    //bruges til export og på resultatside.js
    public EKGListe getSessions(String cpr) throws SQLException {
        SQL.getSqlOBJ().makeConnectionSQL();
        PreparedStatement pp = myConn.prepareStatement("SELECT * FROM SessionData WHERE CPR = ?");
        EKGListe ekgListe = new EKGListe();

        try {
            pp.setString(1, cpr);
            ResultSet rs = pp.executeQuery();
            while (rs.next()) {
                EKG ekg = new EKG();
                ekg.setSessionID(rs.getInt("SessionID"));
                ekg.setStart(rs.getString("Start"));
                ekg.setCPR(rs.getString("CPR"));

                ekgListe.addEKGListe(ekg);

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        SQL.getSqlOBJ().removeConnectionSQL();
        return ekgListe;
    }

    //bruges til export
    public EKGListe exportEKG(int sessionID) throws SQLException{
        SQL.getSqlOBJ().makeConnectionSQL();
        PreparedStatement pp = myConn.prepareStatement("SELECT * FROM EKGData where SessionID = ?");
        EKGListe ekgListe = new EKGListe();

        try {
            pp.setInt(1, sessionID);
            ResultSet rs = pp.executeQuery();

            while(rs.next()){
                EKG ekg = new EKG();
                ekg.setSessionID(rs.getInt("SessionID"));
                ekg.setValues(rs.getDouble("Value"));

                ekgListe.addEKGListe(ekg);
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }
        SQL.getSqlOBJ().removeConnectionSQL();
        return ekgListe;
    }

    //bruges på resultatside til chart
    public List<Double> getEKGData(int sesID){

        List<Double> ekgData = new ArrayList<>();
        try {
            SQL.getSqlOBJ().makeConnectionSQL();
            PreparedStatement pp = myConn.prepareStatement("SELECT Value FROM EKGData WHERE SessionID = ?");
            pp.setInt(1, sesID);

            ResultSet rs = pp.executeQuery();
            while (rs.next()) {
                Double data = rs.getDouble("Value");
                ekgData.add(data);
                // hvordan tager man ud i batch?
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        SQL.getSqlOBJ().removeConnectionSQL();
        return ekgData;
    }

}

