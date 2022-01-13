package controller;


import dataAccesLayer.SQL;
import exceptions.OurException;
import model.Aftale;
import model.AftaleListe;
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

    public AftaleListe cprSearch(String cpr) throws SQLException, OurException {
        if (cpr == null) {
            return SQL.getSqlOBJ().getAftalerListe();
        }
        if (cprCheck(cpr)) {
            return SQL.getSqlOBJ().cprSearch(cpr);
        }
       return new AftaleListe();
    }

    public String insertEKGdataIDatabase(Integer id, String note) throws OurException {
        Aftale aftale = new Aftale();

            if (note.length() < 20000000) {
                EKG.(id);
                EKG.setEKGdata(note);
                SQL.getSqlOBJ().EKGdataInsert(aftale);
                return "added patient" + aftale;
            } else {
                //forkert note
                OurException ex = new OurException();
                ex.setMessage("Noten overskrider den maksimale grænse for anslag");
                throw ex;
            }

    }

    public Integer InsertSessionID(String cpr) throws OurException {
        Aftale aftale = new Aftale();

            if (cpr.length() == 10) {
                aftale.setCPR(cpr);
                return
                        SQL.getSqlOBJ().insertSessionIDogCPR(cpr);

            } else {
                //forkert note
                OurException ex = new OurException();
                ex.setMessage("Noten overskrider den maksimale grænse for anslag");
                throw ex;
            }
        }
}
