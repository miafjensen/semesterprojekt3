package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;


public class XMLService {

    @Path("hentAftaler")
    public String hentAftale(@QueryParam("cpr") String cpr) throws UnirestException{
        HttpResponse<String> stringHttpResponse = Unirest.get("https://ekg3.diplomportal.dk/data/aftaler?cpr="+ cpr)
                .header("accept", "application/xml").header("Authorization", "hemmeliglogin").asString();
        String body = stringHttpResponse.getBody();
        return body;
    }


}
