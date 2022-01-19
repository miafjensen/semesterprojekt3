/**

 * @author ${USER}

 * @Date ${DATE}

 */
package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ekgListe")
@XmlSeeAlso(EKG.class)
@XmlAccessorType(XmlAccessType.FIELD)

public class EKGListe {


    @XmlElement(name = "ekgSession")
    List<EKG> ekgListe = new ArrayList<>();

    public List<EKG> getEkgListe() {
        return ekgListe;
    }

    public void setEkgListe(List<EKG> ekgList) {
        this.ekgListe = ekgList;
    }

    public void addEKGListe(EKG ekg) {
        ekgListe.add(ekg);
    }

}
