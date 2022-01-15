package model;

public class EKG {


    private String CPR;
    private Float EKGdata;
    private int sessionId;
    private String dato;


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

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }
}
