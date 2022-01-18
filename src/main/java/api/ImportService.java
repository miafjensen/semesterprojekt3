package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import controller.ImportController;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;

@Path("import")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class ImportService {

    @GET
    public String importerxml(@QueryParam("grp") int grp, @QueryParam("CPR") String cpr) throws SQLException {
        JSONObject jsonobj;
        String[] endpoints = new String[4];
                endpoints[0] = "aftaler?cpr=" + cpr;
                endpoints[1] = "aftaler";
                endpoints[2] = "ekgSessions?cpr=" + cpr;
                endpoints[3] = "ekgSessions";
                endpoints[4] = "ekgSessions/measurements?sessionID=" + cpr;
                //endpoints[5] = "ekgSessions/measurements";

        String endpoint = endpoints[0];

        /*
        HttpResponse<String> stringHttpResponse = Unirest.get("https://ekg3.diplomportal.dk/data" + endpoint)
            .header("accept", "application/xml").header("Authorization", "hemmeliglogin").asString();
    String body = stringHttpResponse.getBody();
                    return body;
         */

        switch (grp) {
            case 1: {
                jsonobj = ImportController.getimportControllerOBJ().getImportJSON("grp1");
                return jsonobj.toString();
            }
            case 2: {
                if (cpr.length() > 5) {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON(("http:ekg2.diplomportal.dk:8080/data" + endpoint));
                } else {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON("http:ekg2.diplomportal.dk:8080/data" + endpoint);
                }
                return jsonobj.toString();
            }
            case 3: {
                if (cpr.length() > 5) {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON(("http:ekg3.diplomportal.dk:8080/data" + endpoint));
                } else {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON("http:ekg3.diplomportal.dk:8080/data" + endpoint);
                }
                return jsonobj.toString();
            }
            case 4: {
                if (cpr.length() > 5) {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON(("http:ekg4.diplomportal.dk:8080/data" + endpoint));
                } else {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON("http:ekg4.diplomportal.dk:8080/data" + endpoint);
                }
                return jsonobj.toString();
            }
            case 5: {
                if (cpr.length() > 5) {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON(("http://130.225.170.165:8080/data" + endpoint));
                } else {
                    jsonobj = ImportController.getimportControllerOBJ().getImportJSON("http://130.225.170.165:8080/data" + endpoint);
                }
                return jsonobj.toString();
            }
        }
        return null;
    }

}

