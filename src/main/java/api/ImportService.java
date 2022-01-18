package api;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import controller.ImportController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("import")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class ImportService {

    /*
    String input = "";
    String[] endpoints = new String[4];
    endpoints[0]= "aftaler?cpr=" + input;
    endpoints[1] = "aftaler";
    endpoints[2] = "ekgSessions?cpr=" + input;
    endpoints[3] = "ekgSessions";
    endpoints[4] = "ekgSessions/measurements?sessionID=" + input;
*/

    @GET
    public String hentAftaler(@QueryParam("cpr") String cpr) throws UnirestException{
        ArrayList<String> urlRoots = new ArrayList<>();
        //urlRoots.add("https://ekg2.diplomportal.dk/data");
        urlRoots.add("http://ekg3.diplomportal.dk/data");
        //urlRoots.add("http://ekg4.diplomportal.dk/data");
        //urlRoots.add("http://130.225.170.165:8080/data");

        ArrayList<String> authorization = new ArrayList<>();
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("Bearer hemmeliglogin");
        authorization.add("hemmeliglogin");

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i<urlRoots.size(); i++) {

            HttpResponse<String> stringHttpResponse = Unirest.get(urlRoots.get(i) + "/aftaler?cpr=" + cpr)
                    .header("accept", "application/xml")
                    .header("Authorization", authorization.get(i)).asString();
            String stringHttpResponseBody = stringHttpResponse.getBody();
            JSONObject jsonObject = XML.toJSONObject(stringHttpResponseBody);
            jsonArray.put(jsonObject);

        }

        return jsonArray.toString();
    }
/*
    @Path("hentAftaler")
    @GET
    public String importerxml(@QueryParam("grp") int grp, @QueryParam("cpr") String cpr) throws SQLException, UnirestException {

        JSONObject jsonobj;



        switch (grp) {
            case 1: {
               /*
                try{
                if (cpr.length() > 5) {
                    HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg2.diplomportal.dk:8080/data" + endpoints[0])
                            .header("accept", "application/xml")
                            .header("Authorization", "Bearer hemmeliglogin").asString();
                    String body = stringHttpResponse.getBody();
                    return body;
                } else {
                    HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg2.diplomportal.dk:8080/data" + endpoints[1])
                            .header("accept", "application/xml")
                            .header("Authorization", "Bearer hemmeliglogin").asString();
                    String body = stringHttpResponse.getBody();
                    return body;
                }
            }catch (UnirestException throwables){
                throwables.printStackTrace();
                System.out.println("fejl i import fra gruppe 2" );
            }
                try{
                    if (cpr.length() > 5) {
                        HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg3.diplomportal.dk:8080/data" + endpoints[0])
                                .header("accept", "application/xml")
                                .header("Authorization", "Bearer hemmeliglogin").asString();
                        String body = stringHttpResponse.getBody();
                        return body;
                    } else {
                        HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg3.diplomportal.dk:8080/data" + endpoints[1])
                                .header("accept", "application/xml")
                                .header("Authorization", "Bearer hemmeliglogin").asString();
                        String body = stringHttpResponse.getBody();
                        return body;
                    }
                }catch (UnirestException throwables){
                    throwables.printStackTrace();
                    System.out.println("fejl i import fra gruppe 3" );
                }
                try{
                    if (cpr.length() > 5) {
                        HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg4.diplomportal.dk:8080/data" + endpoints[0])
                                .header("accept", "application/xml")
                                .header("Authorization", "Bearer hemmeliglogin").asString();
                        String body = stringHttpResponse.getBody();
                        return body;
                    } else {
                        HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg4.diplomportal.dk:8080/data" + endpoints[1])
                                .header("accept", "application/xml")
                                .header("Authorization", "Bearer hemmeliglogin").asString();
                        String body = stringHttpResponse.getBody();
                        return body;
                    }
                }catch (UnirestException throwables){
                    throwables.printStackTrace();
                    System.out.println("fejl i import fra gruppe 4" );
                }

                try{
                    if (cpr.length() > 5) {
                        HttpResponse<String> stringHttpResponse = Unirest.get("http://130.225.170.165:8080/data" + endpoints[0])
                                .header("accept", "application/xml")
                                .header("Authorization", "Bearer hemmeliglogin").asString();
                        String body = stringHttpResponse.getBody();
                        return body;
                    } else {
                        HttpResponse<String> stringHttpResponse = Unirest.get("http://130.225.170.165:8080/data" + endpoints[1])
                                .header("accept", "application/xml")
                                .header("Authorization", "Bearer hemmeliglogin").asString();
                        String body = stringHttpResponse.getBody();
                        return body;
                    }
                }catch (UnirestException throwables){
                    throwables.printStackTrace();
                    System.out.println("fejl i import fra gruppe 5" );
                }
            }
            case 2: {
                {
                    try{
                        if (cpr.length() > 5) {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg2.diplomportal.dk:8080/data" + endpoints[2])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        } else {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg2.diplomportal.dk:8080/data" + endpoints[3])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        }
                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 2" );
                    }
                    try{
                        if (cpr.length() > 5) {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg3.diplomportal.dk:8080/data" + endpoints[2])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        } else {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg3.diplomportal.dk:8080/data" + endpoints[3])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        }
                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 3" );
                    }
                    try{
                        if (cpr.length() > 5) {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg4.diplomportal.dk:8080/data" + endpoints[2])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        } else {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg4.diplomportal.dk:8080/data" + endpoints[3])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        }
                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 4" );
                    }
                    try{
                        if (cpr.length() > 5) {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http://130.225.170.165:8080/data" + endpoints[2])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        } else {
                            HttpResponse<String> stringHttpResponse = Unirest.get("http://130.225.170.165:8080/data" + endpoints[3])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;
                        }
                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 5" );
                    }
                }
            }
            case 3: {
                {
                    try{

                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg2.diplomportal.dk:8080/data" + endpoints[4])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;

                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 2" );
                    }
                    try{

                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg3.diplomportal.dk:8080/data" + endpoints[4])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;

                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 3" );
                    }
                    try{

                            HttpResponse<String> stringHttpResponse = Unirest.get("http:ekg4.diplomportal.dk:8080/data" + endpoints[4])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;

                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 4" );
                    }
                    try{

                            HttpResponse<String> stringHttpResponse = Unirest.get("http://130.225.170.165:8080/data" + endpoints[4])
                                    .header("accept", "application/xml")
                                    .header("Authorization", "Bearer hemmeliglogin").asString();
                            String body = stringHttpResponse.getBody();
                            return body;

                    }catch (UnirestException throwables){
                        throwables.printStackTrace();
                        System.out.println("fejl i import fra gruppe 5" );
                    }
                }
            }

        }
        return null;
    }*/
}

