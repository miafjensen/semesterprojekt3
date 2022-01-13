package api;


import controller.AftaleController;
import exceptions.OurException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("EKGService")
@Produces({MediaType.TEXT_PLAIN})

public class ResultatService {

    //Henter data fra python til backend
    @POST
    public String postEKGData(String EKGdata) throws OurException {
        //System.out.println(EKGdata);
        String[] a=EKGdata.split(" : ");
        System.out.println(a[0]); //Sådan gør man yeees
        String[] split = a[1].split(",");
        Integer id= AftaleController.getAftaleControllerOBJ().InsertSessionID(a[0]);
        for (int i = 0; i < split.length; i++) {
            double str1= Double.parseDouble(split[i]);
            System.out.println(split[i]);
            AftaleController.getAftaleControllerOBJ().insertEKGdataIDatabase(id, String.valueOf(Float.parseFloat(split[i])));

            System.out.println("Done");
        }
        System.out.println("DONE");
        return EKGdata;

    }

}
