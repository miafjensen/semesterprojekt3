package api;

import controller.EKGController;
import dataAccesLayer.SQL;
import exceptions.OurException;
import model.EKGListe;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("ekgSessions")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Consumes({MediaType.APPLICATION_XML})

public class EKGService {
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public EKGListe getSessions(@QueryParam("cpr") String cpr) throws SQLException{
        return EKGController.getEkgControllerObj().findSessions(cpr);
    }




    //Henter data fra python til backend
    @Path("EKGdata")
    @POST
    public String postEKGData(String EKGdata) throws OurException, SQLException {
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
        }
        //System.out.println(ekgData[i]);
        EKGController.getEkgControllerObj().insertEKGdataIDatabase(id, datapointBatch);

        System.out.println("DONE");
        return EKGdata;

    }

    @Path("measurements")
    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public EKGListe exportEKG(@QueryParam("sessionID") String sessionID) throws SQLException{
        return EKGController.getEkgControllerObj().exportEKG(sessionID);
    }

    @Path("EKGmeasurements")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Double> visEKGOversigt(@QueryParam("sessionID") int sessionID) throws SQLException {
        return SQL.getSqlOBJ().getEKGData(sessionID);
    }


    @Path("Sessions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public EKGListe visAlleSession() throws SQLException {
        return SQL.getSqlOBJ().getALLSessions();
    }

}
