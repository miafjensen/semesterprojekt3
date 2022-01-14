package api;


import com.google.gson.Gson;
import controller.EKGController;
import dataAccesLayer.SQL;
import exceptions.OurException;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("EKGService")
@Produces({MediaType.TEXT_PLAIN})

public class EKGService {

    //Henter data fra python til backend
    @POST
    public String postEKGData(String EKGdata) throws OurException {
        //System.out.println(EKGdata);
        String[] a=EKGdata.split(" : ");
        String cpr = a[0];
        System.out.println(cpr); //Sådan gør man yeees
        String ekgString = a[1];
        String[] ekgData = ekgString.split(",");
        int id = SQL.getSqlOBJ().createEKGSession(cpr);
        double datapointBatch[]=new double[ekgData.length];
                //EKGController.getEkgControllerObj().InsertSessionID(a[0]);
        for (int i = 0; i < ekgData.length; i++) {
            double datapoint= Double.parseDouble(ekgData[i]);
            datapointBatch[i]=datapoint;
        }
            //System.out.println(ekgData[i]);
            EKGController.getEkgControllerObj().insertEKGdataIDatabase(id,datapointBatch);
            //AftaleController.getAftaleControllerOBJ().insertEKGdataIDatabase(id ,String.valueOf(Float.parseFloat(ekgData[i])));

        System.out.println("DONE");
        return EKGdata;

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public void visEKGOversigt(@QueryParam("CPRnummer") String CPR) throws SQLException {

        //JSONObject jsonObject = EKGController.getEkgControllerObj().showEKGsessionsStart(CPR);

        return ;
    }

    @Path("EKGService")
    @GET
    public String selectFromTime(@QueryParam("from") String from, @QueryParam("to") String to) throws SQLException {
        return new Gson().toJson(SQL.getSqlOBJ().getAftaleListeDateTime(from, to));
    }
}
