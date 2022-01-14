package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ekgListe")
@XmlSeeAlso(EKG.class)
@XmlAccessorType(XmlAccessType.FIELD)
//@jakarta.xml.bind.annotation.XmlRootElement(name="aftaleListe")
//@jakarta.xml.bind.annotation.XmlSeeAlso(Aftale.class)
public class EKGListe {


    @XmlElement(name = "ekgSession")
    //@jakarta.xml.bind.annotation.XmlElement(name="aftale")
    List<EKG> ekgListe = new ArrayList<>();

    public List<EKG> getEkgList() {
        return ekgListe;
    }

    public void setEkgList(List<EKG> ekgList) {
        this.ekgListe = ekgList;
    }

    public void addEKGListe(EKG ekg) {
        ekgListe.add(ekg);
    }

}
