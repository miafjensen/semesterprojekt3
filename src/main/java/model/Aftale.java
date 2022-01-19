/**

 * @author ${tidligere kode fra IT3 grp4}

 * @Date ${DATE}

 */
package model;

public class Aftale {


    private String CPR;
    private String ID;
    private String klinikID;
    private String timeStart;
    private String timeEnd;
    private String notat;


    @Override
    public String toString() {
        return "model.Aftale{" +
                "CPR='" + CPR + '\'' +
                ", ID='" + ID + '\'' +
                ", KlinikID='" + klinikID + '\'' +
                ", TimeStart='" + timeStart + '\'' +
                ", TimeEnd='" + timeEnd + '\'' +
                ", Notat='" + notat + '\'' +
                '}';
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getKlinikID() {
        return klinikID;
    }

    public void setKlinikID(String klinikID) {
        this.klinikID = klinikID;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getNotat() {
        return notat;
    }

    public void setNotat(String notat) {
        this.notat = notat;
    }


}
