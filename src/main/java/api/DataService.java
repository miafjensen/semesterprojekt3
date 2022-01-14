package api;

import controller.ImportController;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("data")
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
public class DataService {
    private ArrayList<Integer> dataArrayList = new ArrayList<>();
    private String[] EKGdata;
    private String input = "";
    private String[] splitData;


    @POST
    public String postEKGData(String EKGdata) {

        return EKGdata;
    }


    public ArrayList<Integer> getDataArrayList() {
        for (String string : EKGdata) {
            //kører det splittede data igennem
            dataArrayList.add(Integer.parseInt(string));
            //konverterer indhold i array til int, og tilføjes til arrayList plads for plads
        }
        return dataArrayList;
    }
}




