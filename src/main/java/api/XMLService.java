package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import java.util.ArrayList;


public class XMLService {



    @Path("hentAftaler")
    @GET
    public String hentAftaler(@QueryParam("cpr") String cpr) throws UnirestException{
        ArrayList<String> urlRoots = new ArrayList<>();
        urlRoots.add("https://ekg2.diplomportal.dk/data");
        urlRoots.add("https://ekg3.diplomportal.dk/data");
        urlRoots.add("https://ekg4.diplomportal.dk/data");
        urlRoots.add("http://130.225.170.165:8080/data");

        ArrayList<String> authorization = new ArrayList<>();
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("hemmeliglogin");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i<urlRoots.size(); i++) {

            HttpResponse<String> stringHttpResponse = Unirest.get(urlRoots.get(i) + "/aftaler?cpr=" + cpr).header("accept", "application/xml").header("Authorization", "hemmeliglogin").asString();
            String stringHttpResponseBody = stringHttpResponse.getBody();
            JSONObject jsonObject = XML.toJSONObject(stringHttpResponseBody);
        }

        return jsonArray.toString();
    }

}
