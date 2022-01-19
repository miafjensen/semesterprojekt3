/**

 * @author ${Mia (& Magnus)}

 * @Date ${jan 2022}

 */
package api;


import com.mashape.unirest.http.exceptions.UnirestException;
import controller.ImportController;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;



@Path("import")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class ImportService {


    @GET
    public String hentAftaler(@QueryParam("cpr") String cpr) throws UnirestException{
        return ImportController.getImportControllerObj().hentAftaler(cpr);
    }

    @Path("ekgSessions")
    @GET
    public String importSessionID(@QueryParam("cpr") String cpr) throws UnirestException{
        return ImportController.getImportControllerObj().importSessionID(cpr);
    }

    @Path("ekgSessions/measurements")
    @GET
    public String importEkgData(@QueryParam("sessionID") String sessionID) throws UnirestException{
        return ImportController.getImportControllerObj().importEkgData(sessionID);
    }
}

