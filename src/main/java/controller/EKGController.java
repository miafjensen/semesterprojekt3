package controller;


import dataAccesLayer.SQL;
import exceptions.OurException;
import model.EKG;

import java.sql.SQLException;

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


    public String insertEKGdataIDatabase(Integer id, double datapoint) throws OurException {

        SQL.getSqlOBJ().EKGdataInsert(id, datapoint);
        return "Indsat i EKGData";


    }
    public String insertEKGdataBatch(Integer id, double[] datapointBatch) throws OurException {

        SQL.getSqlOBJ().EKGdataInsertBatch(id, datapointBatch);
        return "Indsat batch i EKGData";


    }

}
