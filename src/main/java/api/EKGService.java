/**

 * @author ${Magnus & Mia}

 * @Date ${jan 2022}

 */
package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.EKGController;
import dataAccesLayer.SQL;
import exceptions.OurException;
import model.EKGListe;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.List;

@Path("ekgSessions")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@Consumes({MediaType.APPLICATION_XML})

public class EKGService {

    // bruges til export og i resultat.js
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public EKGListe getSessions(@QueryParam("cpr") String cpr) throws SQLException{
        return EKGController.getEkgControllerObj().findSessions(cpr);
    }

    /*
    @GET
    @Path("searchCPR")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
        public String importSessionID(@QueryParam("cpr") String cpr) {

        String urlRoots = "http://130.225.170.165:8080/data";
        String authorization = "hemmeliglogin";

        JSONArray jsonArray = new JSONArray();

            try {
                HttpResponse<String> stringHttpResponse = Unirest.get(urlRoots + "/ekgSessions?cpr=" + cpr)
                        .header("accept", "application/xml")
                        .header("Authorization", authorization).asString();
                String stringHttpResponseBody = stringHttpResponse.getBody();
                JSONObject jsonObject = XML.toJSONObject(stringHttpResponseBody);
                jsonArray.put(jsonObject);
            }catch (UnirestException unirestException){
                unirestException.getCause();
            }

        return jsonArray.toString();
    }
*/

    //Henter data fra python til backend
    @Path("EKGdata")
    @POST
    public String postEKGData(String EKGdata) throws SQLException {
        return EKGController.getEkgControllerObj().postEKGdata(EKGdata);
    }

    //bruges til export
    @Path("measurements")
    @GET
    @Produces({MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON})
    public EKGListe exportEKG(@QueryParam("sessionID") String sessionID) {
        return EKGController.getEkgControllerObj().exportEKG(sessionID);
    }

    //bruges i resultat.js til chart
    @Path("EKGmeasurements")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Double> visEKGOversigt(@QueryParam("sessionID") int sessionID) throws SQLException {
        return SQL.getSqlOBJ().getEKGData(sessionID);

    }

 /*
    @Path("Sessions")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public EKGListe visAlleSession() throws SQLException {
        return SQL.getSqlOBJ().getALLSessions();
    }

  */

}
