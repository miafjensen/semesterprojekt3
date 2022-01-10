package dataAccesLayer;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLtester {
    public static void main(String[] args) throws SQLException {

        Connection con = SQL.getConnection();
        /*
        BrugerDAO brugerDAO = new BrugerDAO();
        try {
            String user = "testbruger";
            brugerDAO.findBruger(user);
        }catch (Exception e){        }
        */


        /*
        KonsultationDao konsultationDao = new KonsultationDao();
        try{
            String CPR = "1234";
            konsultationDao.findKonsultationer(CPR);
        }catch (Exception e){}
        */



        /*
        Statement st = con.createStatement();
        PreparedStatement preparedStatement;
        try {

            ResultSet rs = st.executeQuery("SELECT * FROM Users");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2));
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {


            ResultSet rs = st.executeQuery("SELECT * FROM Aftaler");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getString(5));
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {


            ResultSet rs = st.executeQuery("SELECT * FROM Patients");
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
                System.out.println("----------------------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            String CPR = "1111111111";
            String TimeStart = "2021-12-01 00:10";
            String TimeEnd = "2021-12-01 00:25";
            String Notat = "notat tilføjes her";
            String KlinikID = "grp2";



            String SQLAftaler = "INSERT INTO Aftaler (CPR, TimeStart, TimeEnd, Notat, KlinikID) VALUES (?,?,?,?,?)";
            try {
                preparedStatement = con.prepareStatement(SQLAftaler);
                preparedStatement.setString(1, CPR);
                preparedStatement.setString(2, TimeStart);
                preparedStatement.setString(3, TimeEnd);
                preparedStatement.setString(4, Notat);
                preparedStatement.setString(5, KlinikID);
                preparedStatement.execute();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } */
    }
}
