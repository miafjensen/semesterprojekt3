/**

 * @author ${Magnus & Mia}

 * @Date ${jan 2022}

 */
package controller;

import dataAccesLayer.SQL;
import model.EKGListe;
import java.sql.SQLException;



public class EKGController {

    private EKGController() {}

    static private final EKGController EKG_CONTROLLER_OBJ = new EKGController();

    static public EKGController getEkgControllerObj() {
        return EKG_CONTROLLER_OBJ;
    }

    // boolsk værdi til kontrol af cpr'er
    public boolean cprCheck(String name) {
        try {
            return name.length() == 10;
        } catch (Exception e) {
            return false;
        }
    }

    //indsætter ekg-data i db fra python
    public String insertEKGdataIDatabase(Integer id, double[] datapoint) throws SQLException {

        SQL.getSqlOBJ().EKGdataInsertBatch(id, datapoint);
        return "Indsat i EKGData";

    }

    public String postEKGdata(String EKGdata) throws SQLException{
        //System.out.println(EKGdata);
        String[] a = EKGdata.split(" : "); //sorterer cpr fra
        String cpr = a[0];
        System.out.println(cpr);
        String ekgString = a[1]; //string med alt ekgdata
        String[] ekgData = ekgString.split(","); //splitter string
        int id = SQL.getSqlOBJ().createEKGSession(cpr); //indsætter cpr i db og returnerer sessionID
        double datapointBatch[] = new double[ekgData.length];

        for (int i = 0; i < ekgData.length; i++) {
            double datapoint = Double.parseDouble(ekgData[i]);
            datapointBatch[i] = datapoint;
        }
        //System.out.println(ekgData[i]);
        EKGController.getEkgControllerObj().insertEKGdataIDatabase(id, datapointBatch); //indsætter sessionID og ekg i db
        System.out.println("DONE");
        return EKGdata;
    }


    //henter sessions fra db i resultatside.js
    public EKGListe findSessions(String cpr) throws SQLException {
        if (cpr == null) {
            return SQL.getSqlOBJ().getALLSessions();
        }
        if (cprCheck(cpr)) {
            return SQL.getSqlOBJ().getSessions(cpr);
        }
        return new EKGListe();
    }

    //bruges til export af EKG data
    public EKGListe exportEKG(String sessionID){
        try {
            int sesID = Integer.parseInt(sessionID);
            return SQL.getSqlOBJ().exportEKG(sesID);
        }catch (SQLException throwables){
            System.out.println("ingen EKG-data på sessionID: " + sessionID);
            throwables.printStackTrace();
        }
        return new EKGListe();
    }

}
