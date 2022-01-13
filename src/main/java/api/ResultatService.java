package api;


import controller.EKGController;
import dataAccesLayer.SQL;
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
        String[] a = EKGdata.split(" : ");
        String cpr = a[0];
        System.out.println(cpr); //Sådan gør man yeees
        String ekgString = a[1];
        String[] ekgData = ekgString.split(",");
        int id = SQL.getSqlOBJ().createEKGSession(cpr);
        double datapointBatch[] = new double[ekgData.length];
        for (int i = 0; i < ekgData.length; i++) {
            double datapoint = Double.parseDouble(ekgData[i]);
            datapointBatch[i] = datapoint;
            //System.out.println(ekgData[i]);
            //EKGController.getEkgControllerObj().insertEKGdataIDatabase(id, datapoint);
        }
        EKGController.getEkgControllerObj().insertEKGdataBatch(id, datapointBatch);
        System.out.println("DONE");
        return EKGdata;
    }

}