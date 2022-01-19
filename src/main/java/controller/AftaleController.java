/**

 * @author ${Magnus & Mia}

 * @Date ${DATE}

 */
package controller;

import dataAccesLayer.SQL;
import exceptions.OurException;
import model.Aftale;
import model.AftaleListe;
import java.sql.SQLException;

public class AftaleController {

    private AftaleController() {}

    static private final AftaleController AFTALE_CONTROLLER_OBJ = new AftaleController();

    static public AftaleController getAftaleControllerOBJ() {
        return AFTALE_CONTROLLER_OBJ;
    }

    // boolsk værdi til kontrol af cpr'er
    public boolean cprCheck(String name) {
        try {
            return name.length() == 10;
        } catch (Exception e) {
            return false;
        }
    }
    //laver liste over Aftaler ved cpr søgning
    //bruges til export
    public AftaleListe cprSearch(String cpr) throws SQLException{
        if (cpr == null) {
            return SQL.getSqlOBJ().getAftalerListe();
        }
        if (cprCheck(cpr)) {
            return SQL.getSqlOBJ().cprSearch(cpr);
        }
        return new AftaleListe();
    }

    //Opretter aftaler fra startsidens pop-up form
    public String createAftale(String cpr, String timestart, String timeend, String note) throws OurException {
        Aftale aftale = new Aftale();
        if (cprCheck(cpr)) {
            if (note.length() < 255) {
                aftale.setCPR(cpr);
                aftale.setTimeStart(timestart);
                aftale.setTimeEnd(timeend);
                aftale.setNotat(note);
                aftale.setKlinikID("5");

                SQL.getSqlOBJ().insertAftaleSQL(aftale);
                return "added patient" + aftale;
            } else {
                //forkert note
                OurException ex = new OurException();
                ex.setMessage("For lang note, skal være under 255 tegn.");
                throw ex;
            }
        } else {
            // forkert cpr
            OurException ex = new OurException();
            ex.setMessage("CPR skal være 10 cifre, yyyymmddxxxx");
            throw ex;
        }
    }

}
