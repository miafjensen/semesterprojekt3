package model;

public class EKG {

    private String dato;
    private String CPR;
    private Float EKGdata;
    private int sessionID;
    private String start;
    public Double Values;


    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getCPR() {
        return CPR;
    }

    public void setCPR(String CPR) {
        this.CPR = CPR;
    }

    public Float getEKGdata() {
        return EKGdata;
    }

    public void setEKGdata(Float EKGdata) {
        this.EKGdata = EKGdata;
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Double getValues() {
        return Values;
    }

    public void setValues(Double setValues) {
        this.Values = setValues;
    }

}
