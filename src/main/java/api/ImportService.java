/**

 * @author ${USER}

 * @Date ${DATE}

 */
package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@Path("import")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class ImportService {
    private ArrayList<String> urlRoots = new ArrayList<>();
    private ArrayList<String> authorization = new ArrayList<>();

    @GET
    public String hentAftaler(@QueryParam("cpr") String cpr) throws UnirestException{
        urlRoots.add("http://ekg2.diplomportal.dk:8080/data");
        urlRoots.add("https://ekg3.diplomportal.dk/data");
        urlRoots.add("http://ekg4.diplomportal.dk:8080/data");
        urlRoots.add("http://130.225.170.165:8080/data");

        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("hemmeliglogin");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i<urlRoots.size(); i++) {
            try {

                HttpResponse<String> stringHttpResponse = Unirest.get(urlRoots.get(i) + "/aftaler?cpr=" + cpr)
                        .header("accept", "application/xml")
                        .header("Authorization", authorization.get(i)).asString();
                String stringHttpResponseBody = stringHttpResponse.getBody();
                JSONObject jsonObject = XML.toJSONObject(stringHttpResponseBody);
                jsonArray.put(jsonObject);
            }catch (UnirestException unirestException){
                unirestException.getCause();
            }

        }

        return jsonArray.toString();
    }

    @Path("ekgSessions")
    @GET
    public String importSessionID(@QueryParam("cpr") String cpr) throws UnirestException{
        urlRoots.add("http://ekg2.diplomportal.dk:8080/data");
        urlRoots.add("https://ekg3.diplomportal.dk/data");
        urlRoots.add("http://ekg4.diplomportal.dk:8080/data");
        urlRoots.add("http://130.225.170.165:8080/data");

        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("hemmeliglogin");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i<urlRoots.size(); i++) {
            try {
            HttpResponse<String> stringHttpResponse = Unirest.get(urlRoots.get(i) + "/ekgSessions?cpr=" + cpr)
                    .header("accept", "application/xml")
                    .header("Authorization", authorization.get(i)).asString();
            String stringHttpResponseBody = stringHttpResponse.getBody();
            JSONObject jsonObject = XML.toJSONObject(stringHttpResponseBody);
            jsonArray.put(jsonObject);
            }catch (UnirestException unirestException){
                unirestException.getCause();
            }
        }
        return jsonArray.toString();
    }

    @Path("ekgSessions/measurements")
    @GET
    public String importEkgData(@QueryParam("sessionID") String sessionID) throws UnirestException{
        urlRoots.add("http://ekg2.diplomportal.dk:8080/data");
        urlRoots.add("https://ekg3.diplomportal.dk/data");
        urlRoots.add("http://ekg4.diplomportal.dk:8080/data");
        //urlRoots.add("http://130.225.170.165:8080/data");

        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        //authorization.add("hemmeliglogin");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i<urlRoots.size(); i++) {
            try {

                HttpResponse<String> stringHttpResponse = Unirest.get(urlRoots.get(i) + "/ekgSessions/measurements?sessionID=" + sessionID)
                        .header("accept", "application/xml")
                        .header("Authorization", authorization.get(i)).asString();
                String stringHttpResponseBody = stringHttpResponse.getBody();
                JSONObject jsonObject = XML.toJSONObject(stringHttpResponseBody);
                jsonArray.put(jsonObject);
            }catch (UnirestException unirestException){
                unirestException.getCause();
            }
        }
        return jsonArray.toString();
    }
}

