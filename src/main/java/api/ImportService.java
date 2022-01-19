/**

 * @author ${Mia (& Magnus)}

 * @Date ${jan 2022}

 */
package api;


import com.mashape.unirest.http.exceptions.UnirestException;
import controller.ImportController;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

//bruges til import i import.js
@Path("import")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class ImportService {

    //import af Aftaler
    @GET
    public String hentAftaler(@QueryParam("cpr") String cpr) throws UnirestException{
        return ImportController.getImportControllerObj().hentAftaler(cpr);
    }
    //import af sessions
    @Path("ekgSessions")
    @GET
    public String importSessionID(@QueryParam("cpr") String cpr) throws UnirestException{
        return ImportController.getImportControllerObj().importSessionID(cpr);
    }
    //import af ekg-data
    @Path("ekgSessions/measurements")
    @GET
    public String importEkgData(@QueryParam("sessionID") String sessionID) throws UnirestException{
        return ImportController.getImportControllerObj().importEkgData(sessionID);
    }
}

