package controller;


import dataAccesLayer.SQL;
import exceptions.OurException;
import model.EKG;
import model.EKGListe;

import java.sql.SQLException;
import java.util.ArrayList;

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


    public String insertEKGdataIDatabase(Integer id, double[] datapoint) throws OurException {

        SQL.getSqlOBJ().EKGdataInsertBatch(id, datapoint);
        return "Indsat i EKGData";

    }

    public EKGListe showEKGsessionsStart(String CPR) throws SQLException {
        System.out.println(CPR);

        //String sessions = SQL.getSqlOBJ().getSessionID(CPR);

        return new EKGListe();
    }

    public ArrayList<EKG> showEKGdataToChart(int sessionID) throws SQLException {


        ArrayList<EKG> ekgData = SQL.getSqlOBJ().getEKGData(sessionID);

        return ekgData;
    }


/*
 public AftaleListe cprSearch(String cpr) throws SQLException, OurException {
        if (cpr == null) {
            return SQL.getSqlOBJ().getAftalerListe();
        }
        if (cprCheck(cpr)) {
            return SQL.getSqlOBJ().cprSearch(cpr);
        }
       return new AftaleListe();
    }
 */


   /* public String insertEKGdataBatch(Integer id, double[] datapointBatch) throws OurException {

        SQL.getSqlOBJ().EKGdataInsertBatch(id, datapointBatch);
        return "Indsat batch i EKGData";
    } */


  /*  public String insertwhatintoDatabase(String EKGdata) throws OurException {
        //System.out.println(EKGdata);
        String[] a=EKGdata.split(" : ");
        String cpr = a[0];
        System.out.println(cpr); //Sådan gør man yeees
        String ekgString = a[1];
        String[] ekgData = ekgString.split(",");
        int id = SQL.getSqlOBJ().createEKGSession(cpr);
        double datapointBatch[]=new double[ekgData.length];
        //EKGController.getEkgControllerObj().InsertSessionID(a[0]);
        for (int i = 0; i < ekgData.length; i++) {
            double datapoint= Double.parseDouble(ekgData[i]);
            datapointBatch[i]=datapoint;
        }
        //System.out.println(ekgData[i]);
        EKGController.getEkgControllerObj().insertEKGdataIDatabase(id,datapointBatch);
        //AftaleController.getAftaleControllerOBJ().insertEKGdataIDatabase(id ,String.valueOf(Float.parseFloat(ekgData[i])));

        System.out.println("DONE");
        return EKGdata;

    } */

}
