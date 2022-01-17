package controller;

import dataAccesLayer.SQL;
import exceptions.OurException;
import model.EKG;
import model.EKGListe;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EKGController {

    private EKGController() {
    }

    static private final EKGController EKG_CONTROLLER_OBJ = new EKGController();

    static public EKGController getEkgControllerObj() {
        return EKG_CONTROLLER_OBJ;
    }

    // bolsk værdi til kontrol af cpr'er
    public boolean cprCheck(String name) {
        try {
            double test = Double.parseDouble(name);
            return name.length() == 10;
        } catch (Exception e) {
            return false;
        }
    }


    public String insertEKGdataIDatabase(Integer id, double[] datapoint) throws SQLException {

        SQL.getSqlOBJ().EKGdataInsertBatch(id, datapoint);
        return "Indsat i EKGData";

    }


    public EKGListe findSessions(String cpr) throws SQLException {
        if (cpr == null) {
            return SQL.getSqlOBJ().getALLSessions();
        }
        if (cprCheck(cpr)) {
            return SQL.getSqlOBJ().getSessions(cpr);
        }
        return new EKGListe();
    }

    public EKGListe exportEKG(String sessionID) throws SQLException{

        try {
            int sesID = Integer.parseInt(sessionID);
            return SQL.getSqlOBJ().exportEKG(sesID);
        }catch (SQLException throwables){
            System.out.println("ingen EKG-data på sessionID: " + sessionID);
            throwables.printStackTrace();
        }
        return new EKGListe();
    }

    /* public EKGListe showEKGsessionsStart(String CPR) throws SQLException {
        System.out.println(CPR);

        return new EKGListe();
    } */

    /*
    public List<Double> showEKGdataToChart(int sessionID) throws SQLException {

        List<Double> ekgData = SQL.getSqlOBJ().getEKGData(sessionID);

        return ekgData;
    }
    */
}
