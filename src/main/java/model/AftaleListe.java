package model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "aftaleListe")
@XmlSeeAlso(Aftale.class)
@XmlAccessorType(XmlAccessType.FIELD)

public class AftaleListe {

    @XmlElement(name = "aftale")
    List<Aftale> aftaleListe = new ArrayList<>();

    public void setAftaleListe(List<Aftale> aftaleListe) {
        this.aftaleListe = aftaleListe;
    }

    public List<Aftale> getAftaler() {
        return aftaleListe;
    }

    public void addAftaler(Aftale aftale) {
        aftaleListe.add(aftale);
    }
}
